package Services;

import Dao.*;
import Response.ClearResponse;

import java.sql.Connection;

public class ClearService {

//spec    Deletes ALL data from the database, including user, authtoken, person, and event data

    /**
     * Deletes ALL data from the database
     *
     * @return ClearResponse response to clear
     * */
    public ClearResponse clear(){
        Database db = new Database();
        try {
            db.openConnection();

            clearGivenConnection(db.getConnection());
            db.closeConnection(true);

            ClearResponse result = new ClearResponse("Clear Succeeded", true);

            return result;

        } catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);

            ClearResponse result = new ClearResponse("Clear Failed", false);

            return result;

        }
    }

    private void clearGivenConnection(Connection connection) throws DataAccessException {
        new EventDao(connection).clear();
        new UserDao(connection).clear();
        new AuthtokenDao(connection).clear();
        new PersonDao(connection).clear();

    }

}
