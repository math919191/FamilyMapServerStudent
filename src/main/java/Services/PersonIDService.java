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

            //get the username of the person by using the authtoken

            AuthtokenDao authtokenDao = new AuthtokenDao(db.getConnection());
            AuthToken authToken1 = authtokenDao.findUserName(givenAuthtoken);


            if (authToken1 == null){
                throw new Exception("Invalid auth token");
            }
            String usernameFromAuthToken = authToken1.getUsername();

            //verify the usernames match
            if (!usernameFromAuthToken.equals(person.getAssociatedUsername())){
                throw new Exception("Usernames do not match...invalid something");
            }

            //create a response and send it back
            db.closeConnection(false);


            PersonIDResponse response = getPersonIDResponseFromPerson(person);
            return response;

        } catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            ErrorResponse result = new ErrorResponse("Person ID service failed", false);
            return result;

        }

    }

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
