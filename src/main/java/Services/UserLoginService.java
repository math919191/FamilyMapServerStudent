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

public class UserLoginService {


/**
 * logs user in and returns an authtoken
 *
 * @param userLoginRequest a userLoginRequest
 * @return userLoginResponse a userLoginResponse
*/

    public Response userLogin(UserLoginRequest userLoginRequest){
        //Logs the user in
        //Returns an authtoken.

        Database db = new Database();
        try {
            db.openConnection();

            if (!hasValidRequest(userLoginRequest)){
                throw new Exception("missing username or password");
            }

            User user = getUser(userLoginRequest.getUsername(), db.getConnection());
            if (user == null){
                throw new Exception("User doesn't exist");
            }

            boolean valid = isUsernameAndPasswordValid(userLoginRequest.getUsername(), userLoginRequest.getPassword(), user);

            if (!valid){
                throw new Exception("Invalid username or password");
            }


            String authToken = getAuthToken(user, db.getConnection());

            db.closeConnection(true);

            UserLoginResponse response = new UserLoginResponse(authToken, user.getUsername(), user.getPersonID(), true );

            return response;

        }   catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            ErrorResponse result = new ErrorResponse("User login Failed", false);
            return result;

        }


    }

    private String getAuthToken(User user, Connection connection) throws DataAccessException {
        AuthToken authToken = new AuthtokenDao(connection).findAuthToken(user.getUsername());
        return authToken.getAuthtoken();
    }

    private boolean isUsernameAndPasswordValid(String username, String password, User user) {
        if (username.equals(user.getUsername()) && password.equals(user.getPassword())){
            return true;
        } else {
            return false;
        }
    }

    private User getUser(String username, Connection connection) throws DataAccessException {
        User user = new UserDao(connection).findUserFromUserName(username);
        return user;
    }

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
