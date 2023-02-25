package Services;

import Dao.AuthtokenDao;
import Dao.DataAccessException;
import Dao.Database;
import Dao.UserDao;
import Model.AuthToken;
import Model.User;
import Request.UserLoginRequest;
import Response.ErrorResponse;
import Response.UserLoginResponse;
import Response.Response;

import java.sql.Connection;
import java.util.UUID;

/** Logs the user in */
public class UserLoginService {


/**
 * logs user in and returns an authtoken
 *
 * @param userLoginRequest a userLoginRequest
 * @return userLoginResponse a userLoginResponse
*/

    public Response userLogin(UserLoginRequest userLoginRequest){
        Database db = new Database();
        try {
            db.openConnection();

            if (!hasValidRequest(userLoginRequest)){
                throw new Exception("missing username or password - bad request");
            }

            User user = getUser(userLoginRequest.getUsername(), db.getConnection());
            if (user == null){
                throw new Exception("User doesn't exist - bad request");
            }

            boolean valid = isUsernameAndPasswordValid(userLoginRequest.getUsername(), userLoginRequest.getPassword(), user);

            if (!valid){
                throw new Exception("Invalid username or password - bad request");
            }


            //generate a new auth token for the user's login
            String authToken = UUID.randomUUID().toString();
            AuthToken authToken1 = new AuthToken(authToken, user.getUsername());
            new AuthtokenDao(db.getConnection()).insertAuthToken(authToken1);

            db.closeConnection(true);

            UserLoginResponse response = new UserLoginResponse(authToken, user.getUsername(), user.getPersonID(), true );

            return response;

        }   catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            ErrorResponse result = new ErrorResponse("User login Failed " + ex.getMessage(), false);
            return result;

        }


    }

    /**
     * checks if the username and password are associated with the given username
     * @param username the username given
     * @param password the password given
     * @param user the user potentially associated with given credentials
     * */
    private boolean isUsernameAndPasswordValid(String username, String password, User user) {
        if (username.equals(user.getUsername()) && password.equals(user.getPassword())){
            return true;
        } else {
            return false;
        }
    }

    /**
     * gets a user from username and connection
     * @param username given username
     * @param connection given password
     * */
    private User getUser(String username, Connection connection) throws DataAccessException {
        User user = new UserDao(connection).findUserFromUserName(username);
        return user;
    }

    /**
     * checks if the password and username are not null or empty
     * @param userLoginRequest login request that contains username nad password
     * */
    private boolean hasValidRequest(UserLoginRequest userLoginRequest) {
        if (userLoginRequest.getPassword() != null
                && userLoginRequest.getUsername() != null
                && userLoginRequest.getUsername() != ""
                && userLoginRequest.getPassword() != "")
        {
            return true;
        } else {
            return false;
        }
    }

}
