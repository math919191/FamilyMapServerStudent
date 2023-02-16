package Services;

import Response.PersonUserResponse;

public class PersonUser {

    /**
     * Returns ALL family members of the current user.
     * The current user is determined by the provided authtoken.
     *
     * @return PersonUserResponse the personUserResponse
     *
     * */


    PersonUserResponse personUser(){
        //get username from authtoken
            // username = authtokenDao.getUsername(authtoken)
        //get all the people with the associated username
            // allThePeople = personDao.getAllFamilyMembers(username)
        //convert allThePeople into a PersonUserResponse
        // return the response
        return null;
    }

}
