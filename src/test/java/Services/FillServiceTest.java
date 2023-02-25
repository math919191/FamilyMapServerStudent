package Services;

import Dao.DataAccessException;
import Model.AuthToken;
import Model.Data.GenerateData;
import Model.Person;
import Model.User;
import Response.Response;
import Response.ErrorResponse;
import Response.FillResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FillServiceTest extends TestHelperFunctions {

    GenerateData generateData;
    FillService fillService;
    AuthToken authToken;
    User user;

    @BeforeEach
    void setUp() throws DataAccessException {
        //clear all the data for a fresh start
        new ClearService().clear();

        //generate data class to be able to get data
        generateData = new GenerateData();
        fillService = new FillService();

        //create dummy data
        user = generateData.generateRandomUser();
        authToken = generateData.generateRandomAuthTokenWithUsername(user.getUsername());

        addUser(user);
        addAuthToken(authToken);

    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    void FillServiceFail(){

        //give a bad username
        Response response = fillService.fill("fake username", 4);
        //verify the response is an error response
        assertEquals(response.getClass(), ErrorResponse.class);

        //verify the response contains "invalid username"
        ErrorResponse errorResponse = (ErrorResponse) response;
        assertTrue(errorResponse.getMessage().contains("invalid username"));

    }

    @Test
    void FillServicePass() throws DataAccessException {
        Response response = fillService.fill(user.getUsername(), 4);

        //check returns a valid response
        assertEquals(response.getClass(), FillResponse.class);

        //cast to event FillResponse
        FillResponse fillResponse = (FillResponse) response;

        //get all the people in the database associated with the user
        ArrayList<Person> persons = findPersons(user.getUsername());

        //There should be 31 persons with 4 generations
        assertEquals(31, persons.size());



        //call fill service with 0 generation
        response = fillService.fill(user.getUsername(), 0);

        //cast response
        fillResponse = (FillResponse) response;

        //There should be one person
        persons = findPersons(user.getUsername());
        assertEquals( 0, persons.size());


        //call fill service with 2 generations
        response = fillService.fill(user.getUsername(), 1);

        //cast response
        fillResponse = (FillResponse) response;

        //There should be 3 persons
        persons = findPersons(user.getUsername());
        assertEquals(persons.size(), 3);


    }


}