package Services;

import Dao.AuthtokenDao;
import Dao.Database;
import Dao.PersonDao;
import Model.AuthToken;
import Model.Person;
import Response.ErrorResponse;
import Response.LoadResponse;
import Response.PersonIDResponse;
import Response.Response;

/** returns a person based on the given personID */
public class PersonIDService {
    /**
     * Returns the single Person object with the specified ID (if the person is associated with the current user).
     * The current user is determined by the provided authtoken.
     *
     * @param personID the personID
     * @return PersonIDResponse the personIDResponse
     * */


    public Response getPersonFromPersonID(String personID, String givenAuthtoken){
        Database db = new Database();
        try {
            db.openConnection();

            //get the person with the id
            Person person = new PersonDao(db.getConnection()).findPerson(personID);

            if (person == null){
                throw new Exception("invalid personID - bad request");
            }

            //get the username of the person by using the authtoken

            AuthtokenDao authtokenDao = new AuthtokenDao(db.getConnection());
            AuthToken authToken1 = authtokenDao.findUserName(givenAuthtoken);


            if (authToken1 == null){
                throw new Exception("invalid authToken - bad request");
            }
            String usernameFromAuthToken = authToken1.getUsername();

            //verify the usernames match
            if (!usernameFromAuthToken.equals(person.getAssociatedUsername())){
                throw new Exception("Usernames do not match...invalid something bad request ");
            }

            db.closeConnection(false);

            //create a response and send it back
            PersonIDResponse response = getPersonIDResponseFromPerson(person);
            return response;

        } catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            ErrorResponse result = new ErrorResponse("Person ID service failed " + ex.getMessage(), false);
            return result;

        }

    }

    /** Convert the person object to a response
     * @param person person to be converted to a response
     * */
    private PersonIDResponse getPersonIDResponseFromPerson(Person person) {

        return new PersonIDResponse(
                person.getAssociatedUsername(),
                person.getPersonID(),
                person.getFirstName(),
                person.getLastName(),
                person.getGender(),
                person.getFatherID(),
                person.getMotherID(),
                person.getSpouseID(),
                true
        );
    }
}
