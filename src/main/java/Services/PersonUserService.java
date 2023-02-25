package Services;

import Dao.AuthtokenDao;
import Dao.Database;
import Dao.PersonDao;
import Model.AuthToken;
import Model.Person;
import Response.PersonUserResponse;
import Response.ErrorResponse;
import Response.Response;

import java.util.ArrayList;
/** returns all the users assoicated with a user */
public class PersonUserService {

    /**
     * Returns ALL family members of the current user.
     * The current user is determined by the provided authtoken.
     *
     * @return PersonUserResponse the personUserResponse
     *
     * */


    public Response personUser(String authToken){


        Database db = new Database();
        try {
            db.openConnection();

            //get username from authtoken
            //get the username of the person by using the authtoken
            AuthtokenDao authtokenDao = new AuthtokenDao(db.getConnection());
            AuthToken authToken1 = authtokenDao.findUserName(authToken);

            if (authToken1 == null){
                throw new Exception("invalid authtoken - bad request");
            }
            String username = authToken1.getUsername();


            //get all the people with the associated username
            PersonDao personDao = new PersonDao(db.getConnection());
            ArrayList<Person> persons = personDao.findPersons(username);


            db.closeConnection(false);

            //create a response and send it back
            PersonUserResponse response = new PersonUserResponse(persons, true);

            return response;

        } catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            ErrorResponse result = new ErrorResponse("Person User service failed " + ex.getMessage(), false);
            return result;

        }
    }

}
