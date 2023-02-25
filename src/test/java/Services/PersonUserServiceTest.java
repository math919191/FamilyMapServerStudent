package Services;

import Dao.DataAccessException;
import Model.AuthToken;
import Model.Data.GenerateData;
import Model.Event;
import Model.Person;
import Model.User;
import Response.Response;
import Response.PersonUserResponse;
import Response.ErrorResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonUserServiceTest extends TestHelperFunctions {
    GenerateData generateData;
    PersonUserService personUserService;
    AuthToken authToken;
    User user;
    @BeforeEach
    void setUp() throws DataAccessException {


        //clear all the data for a fresh start
        new ClearService().clear();

        //generate data class to be able to get data
        generateData = new GenerateData();
        personUserService = new PersonUserService();

        //create dummy data
        user = generateData.generateRandomUser();
        authToken = generateData.generateRandomAuthTokenWithUsername(user.getUsername());


    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    void PersonUserFail() throws DataAccessException {

        Response response = personUserService.personUser("bad auth token");
        assertEquals(response.getClass(), ErrorResponse.class);
        ErrorResponse errorResponse = (ErrorResponse) response;
        assertTrue(errorResponse.getMessage().contains("invalid authtoken"));


        addUser(user);
        addAuthToken(authToken);

        response = personUserService.personUser(authToken.getAuthtoken());

        //cast to Person all response
        PersonUserResponse personUserResponse = (PersonUserResponse) response;

        //There should only be nothing in it.
        assertEquals(personUserResponse.getData().size(), 0);

    }



    @Test
    void PersonUserPass() throws DataAccessException {

        addUser(user);
        addAuthToken(authToken);


        //insert another unrelated Person to the user
        Person person3 = generateData.generateRandomPersonWithUsername(user.getUsername());
        addPerson(person3);

        //get the response, it should be the same size
        PersonUserResponse personUserResponse = (PersonUserResponse) personUserService.personUser(authToken.getAuthtoken());
        assertEquals(personUserResponse.getData().size(), 1);

        //get the item ID of the first element, it should match what we inserted
        assertEquals(personUserResponse.getData().get(0).getPersonID(), person3.getPersonID());


        //add an associated person to the username
        Person person1 = generateData.generateRandomPersonWithUsername(user.getUsername());
        addPerson(person1);

        personUserResponse = (PersonUserResponse) personUserService.personUser(authToken.getAuthtoken());

        // insert another person with the same username
        Person person2 = generateData.generateRandomPersonWithUsername(user.getUsername());
        addPerson(person2);
        //There should now be 2 items inside of the response
        personUserResponse = (PersonUserResponse) personUserService.personUser(authToken.getAuthtoken());
        assertEquals(personUserResponse.getData().size(), 3);

    }


}