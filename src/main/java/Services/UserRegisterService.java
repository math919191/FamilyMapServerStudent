package Services;

import Dao.AuthtokenDao;
import Dao.DataAccessException;
import Dao.Database;
import Dao.UserDao;
import Model.AuthToken;
import Model.Data.GenerateFamilyTree;
import Model.User;
import Request.UserRegisterRequest;
import Response.ErrorResponse;
import Response.Response;
import Response.UserRegisterResponse;

import java.sql.Connection;
import java.util.UUID;

public class UserRegisterService {

/*  From spec
    URL Path: /user/register
    Description:
    Creates a new user account (user row in the database)
    Generates 4 generations of ancestor data for the new user
        (just like the /fill endpoint if called with a generations value of 4 and this new user’s username as parameters)
    Logs the user in
    Returns an authtoken

    Possible Errors: Request property missing or has invalid value,
        Username already taken by another user, Internal server error
*/

    /**
     * Creates user and logs them in
     *
     * @param userRegisterRequest the userRegisterRequest
     * @return UserRegisterResponse
     */

    public Response userRegister(UserRegisterRequest userRegisterRequest){
        Database db = new Database();
        try {
            db.openConnection();

            //Creates a new user account (user row in the database)
            if (usernameIsTaken(userRegisterRequest.getUsername(), db.getConnection() )){
                throw new Exception("username taken");
            }
            if (!allFieldsValid(userRegisterRequest)){
                throw new Exception("invalid fields");
            }
//            TODO check this
            User user = makeUser(userRegisterRequest);
            new UserDao(db.getConnection()).insertUser(user);

            //Generates 4 generations of ancestor data for the new user
            GenerateFamilyTree t = new GenerateFamilyTree(userRegisterRequest.getUsername(), db.getConnection());
            t.generateTree(4);

            //Logs the user in
            String authToken = getRandomID();
            insertAuthTokenInDB(authToken, userRegisterRequest.getUsername(), db.getConnection());

            db.closeConnection(true);

            //Returns an authtoken
            UserRegisterResponse response = new UserRegisterResponse(authToken, userRegisterRequest.getUsername(), user.getPersonID(), true);
            return response;

        }  catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            ErrorResponse result = new ErrorResponse("User registration Failed", false);
            return result;

        }
    }

    private void insertAuthTokenInDB(String authToken, String username, Connection connection) throws DataAccessException {
        AuthToken token = new AuthToken(authToken, username);
        new AuthtokenDao(connection).insertAuthToken(token);
    }

    private User makeUser(UserRegisterRequest req){
        return new User(req.getUsername(), req.getPassword(), req.getEmail(), req.getFirstName(), req.getLastName(), req.getGender(), getRandomID());
    }

    private void insertUserInDB(User user, Connection connection) throws DataAccessException {
        new UserDao(connection).insertUser(user);
    }

    private boolean usernameIsTaken(String username, Connection connection) throws DataAccessException {

        User u = new UserDao(connection).findUserFromUserName(username);
        if (u == null){
            return false;
        } else {
            return true;
        }
    }

    private String getRandomID(){
        return UUID.randomUUID().toString();
    }

    private boolean allFieldsValid(UserRegisterRequest r){
        //TODO write this... what is considered valid?
        return true;
    }

}
