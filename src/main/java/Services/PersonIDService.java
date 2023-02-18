package Services;

import Response.PersonIDResponse;

public class PersonIDService {
    /**
     * Returns the single Person object with the specified ID (if the person is associated with the current user).
     * The current user is determined by the provided authtoken.
     *
     * @param personID the personID
     * @return PersonIDResponse the personIDResponse
     * */


    PersonIDResponse personIDResponse(String personID){
        //get the person with the id
            //calls personDao.findPerson(personID)
        //get the username of the person by using the authtoken
            //calls authTokenDao.findUsername(authtoken)
        //verify the usernames match
            // person.getUsername() = authToken.getUserName()
        //create a response and send it back

        return null;
    }
}
