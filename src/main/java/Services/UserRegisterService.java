package Services;

import Request.UserRegisterRequest;
import Response.UserRegisterResponse;

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

    public UserRegisterResponse userRegister(UserRegisterRequest userRegisterRequest){
//        Creates a new user account (user row in the database)
            //call insert user
//        Generates 4 generations of ancestor data for the new user
//                (just like the /fill endpoint if called with a generations value of 4 and this new user’s username as parameters)
//        Logs the user in
//        Returns an authtoken
//
//        Possible Errors: Request property missing or has invalid value,
//                Username already taken by another user, Internal server error

        return null;
    }
}
