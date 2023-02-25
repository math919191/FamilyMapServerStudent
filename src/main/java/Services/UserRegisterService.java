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
/** registers user and then creates family tree data for them */
public class UserRegisterService {

    /**
     * Creates a new user account (user row in the database)
     * Generates 4 generations of ancestor data for the new user
     * Logs the user in
     * Returns an authtoken
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

    /** puts auth token in the database
     * @param authToken given authtoken
     * @param username given username
     * @param connection given database connection
     * */
    private void insertAuthTokenInDB(String authToken, String username, Connection connection) throws DataAccessException {
        AuthToken token = new AuthToken(authToken, username);
        new AuthtokenDao(connection).insertAuthToken(token);
    }
    /**
     * Creates a user with the register request
     * @param req user register request to create User
     * */

    private User makeUser(UserRegisterRequest req){
        return new User(req.getUsername(), req.getPassword(), req.getEmail(), req.getFirstName(), req.getLastName(), req.getGender(), getRandomID());
    }

    /**
     * Checks if a username has already been used
     * @param username given username
     * @param connection given connection
     * @return if the username has been taken
     * */
    private boolean usernameIsTaken(String username, Connection connection) throws DataAccessException {

        User u = new UserDao(connection).findUserFromUserName(username);
        if (u == null){
            return false;
        } else {
            return true;
        }
    }

    /**
     * generates a random ID using the UUID class
     * @return random ID
     * */
    private String getRandomID(){
        return UUID.randomUUID().toString();
    }

    /**
     * verify the given fields are valid
     * @param r user register request with information
     * */
    private boolean allFieldsValid(UserRegisterRequest r){
        if (r.getUsername() != null &&
            r.getPassword() != null &&
                r.getFirstName() != null &&
                r.getLastName() != null &&
                r.getEmail() != null
        ){
            return true;
        } else {
            return false;
        }
    }

}
