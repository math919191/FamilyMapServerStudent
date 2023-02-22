package Services;

import Dao.*;
import Model.Data.GenerateFamilyTree;
import Response.ClearResponse;
import Response.Response;

import Response.ErrorResponse;
import Response.FillResponse;

import java.sql.Connection;

public class FillService {

    /**
     * Populates the server's database with generated data for the specified username. The required "username" parameter must be a user already registered with the server.
     * If there is any data in the database already associated with the given username, it is deleted.
     * The optional "generations" parameter lets the caller specify the number of generations of ancestors to be generated,
     * and must be a non-negative integer (the default is 4, which results in 31 new persons each with associated events).
     *
     * @param username a username
     * @param generations number of generations (optional, default=4)
     *
     * @return FillResponse
     * */

    public Response fill(String username, int generations){

        Database db = new Database();
        try {
            db.openConnection();

            if (userIsRegistered(username, db.getConnection())){
                deleteAnyDataAssociatedWithUser(username, db.getConnection());
            } else {
                throw new Exception("invalid username");
            }

            GenerateFamilyTree t = new GenerateFamilyTree(username, db.getConnection());
            t.generateTree(generations);

            db.closeConnection(true);

            FillResponse result = new FillResponse("Clear Succeeded", true);

            return result;

        } catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            ErrorResponse result = new ErrorResponse("Fill Failed", false);
            return result;

        }
    }

    private boolean userIsRegistered(String username, Connection connection) throws DataAccessException {
        UserDao uDao = new UserDao(connection);
        if (uDao.findUserFromUserName(username) == null){
            return false;
        } else {
            return true;
        }

    }

    private void deleteAnyDataAssociatedWithUser(String username, Connection connection) throws DataAccessException {
        PersonDao pDao = new PersonDao(connection);
        pDao.clearWithUsername(username);

        EventDao eDao = new EventDao(connection);
        eDao.clearWithUsername(username);

    }
}
