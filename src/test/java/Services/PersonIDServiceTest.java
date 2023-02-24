package Services;

import Dao.DataAccessException;
import Model.AuthToken;
import Model.Data.GenerateData;
import Model.Person;
import Model.User;
import Response.Response;
import Response.ErrorResponse;
import Response.PersonIDResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonIDServiceTest extends TestHelperFunctions {
    GenerateData generateData;
    PersonIDService personIDService;
    AuthToken authToken;
    User user;
    Person person1;
    Person person2;
    @BeforeEach
    void setUp() throws DataAccessException {

        //clear all the data for a fresh start
        new ClearService().clear();

        //generate data class to be able to get data
        generateData = new GenerateData();
        personIDService = new PersonIDService();

        //create dummy data
        user = generateData.generateRandomUser();
        authToken = generateData.generateRandomAuthTokenWithUsername(user.getUsername());
        person1 = generateData.generateRandomPersonWithUsername(user.getUsername());
        person2 = generateData.generateRandomPersonWithUsername("fake username");

        addUser(user);
        addAuthToken(authToken);
        addPerson(person1);
        addPerson(person2);

    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    void personIDFail(){

        // give a bad person ID
        Response response = personIDService.getPersonFromPersonID("bad person id", authToken.getAuthtoken());
        assertEquals(response.getClass(), ErrorResponse.class);
        ErrorResponse errorResponse = (ErrorResponse) response;
        assertTrue(errorResponse.getMessage().contains("invalid personID"));

        //give a bad authtoken
        response = personIDService.getPersonFromPersonID(person1.getPersonID(), "bad authtoken");
        assertEquals(response.getClass(), ErrorResponse.class);
        errorResponse = (ErrorResponse) response;
        assertTrue(errorResponse.getMessage().contains("invalid authToken"));

        //give valid Person ID and a valid authtoken, but make them not match
        response = personIDService.getPersonFromPersonID(person2.getPersonID(), authToken.getAuthtoken());
        assertEquals(response.getClass(), ErrorResponse.class);
        errorResponse = (ErrorResponse) response;
        assertTrue(errorResponse.getMessage().contains("do not match"));


    }



    @Test
    void personIDPass() throws DataAccessException {

        Response response = personIDService.getPersonFromPersonID(person1.getPersonID(), authToken.getAuthtoken());

        //check returns a valid response
        assertEquals(response.getClass(), PersonIDResponse.class);

        //cast to Person PersonIDResponse
        PersonIDResponse personIDResponse = (PersonIDResponse) response;

        //We should get the same Person back
        assertEquals(personIDResponse.getPersonID(), person1.getPersonID());

    }


}