package Services;

import Dao.DataAccessException;
import Model.AuthToken;
import Model.Data.GenerateData;
import Model.User;
import Request.LoadRequest;
import Response.Response;
import Response.LoadResponse;

import Response.ErrorResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LoadServiceTest extends TestHelperFunctions {

    GenerateData generateData;
    LoadService loadService;
    AuthToken authToken;
    User user;
    LoadRequest loadRequest;
    @BeforeEach
    void setUp() throws DataAccessException {

        //clear all the data for a fresh start
        new ClearService().clear();

        //generate data class to be able to get data
        generateData = new GenerateData();
        loadService = new LoadService();

        //create dummy data
        user = generateData.generateRandomUser();
        authToken = generateData.generateRandomAuthTokenWithUsername(user.getUsername());
        loadRequest = generateData.generateLoadRequest();

        addUser(user);
        addAuthToken(authToken);

    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    void LoadServiceFail(){
        //there isn't a great example for having this fail.
        // so... we'll add the data twice and the load service should handle it
        Response response = loadService.load(loadRequest);
        loadService.load(loadRequest);
        assertEquals(response.getClass(), LoadResponse.class);
    }

    @Test
    void LoadServicePass(){
        Response response = loadService.load(loadRequest);

        //check returns a valid response
        assertEquals(response.getClass(), LoadResponse.class);

        //cast to event EventIDResponse
        LoadResponse loadResponse = (LoadResponse) response;

        //check they were added.
        assertEquals(loadResponse.getMessage(), "Successfully added 1 users, 1 persons, and 1 events to the database.");


    }

}