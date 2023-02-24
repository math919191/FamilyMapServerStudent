package Services;

import Dao.*;
import Model.AuthToken;
import Model.Data.GenerateData;
import Model.Event;
import Model.User;
import Response.ErrorResponse;
import Response.EventAllResponse;
import Response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EventAllServiceTest extends TestHelperFunctions{

    GenerateData generateData;
    EventAllService eventAllService;
    AuthToken authToken;
    User user;
    @BeforeEach
    void setUp() throws DataAccessException {


        //clear all the data for a fresh start
        new ClearService().clear();

        //generate data class to be able to get data
        generateData = new GenerateData();
        eventAllService = new EventAllService();

        //create dummy data
        user = generateData.generateRandomUser();
        authToken = generateData.generateRandomAuthTokenWithUsername(user.getUsername());


    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    void EventAllFail(){

        Response response = eventAllService.eventAll("bad auth token");
        assertEquals(response.getClass(), ErrorResponse.class);
        ErrorResponse errorResponse = (ErrorResponse) response;
        assertTrue(errorResponse.getMessage().contains("invalid authtoken"));

    }



    @Test
    void EventAllPass() throws DataAccessException {

        addUser(user);
        addAuthToken(authToken);

        Response response = eventAllService.eventAll(authToken.getAuthtoken());

        //check returns a valid response
        assertEquals(response.getClass(), EventAllResponse.class);

        //cast to event all response
        EventAllResponse eventAllResponse = (EventAllResponse) response;

        //There should only be nothing in it.
        assertEquals(eventAllResponse.getData().size(), 0);


        //insert another unrelated event to the user
        Event event3 = generateData.generateRandomEventWithUsername("fake username");

        //get the response, it should be the same size
        eventAllResponse = (EventAllResponse) eventAllService.eventAll(authToken.getAuthtoken());
        assertEquals(eventAllResponse.getData().size(), 0);

        //add an associated event to the username
        Event event1 = generateData.generateRandomEventWithUsername(user.getUsername());
        addEvent(event1);

        eventAllResponse = (EventAllResponse) eventAllService.eventAll(authToken.getAuthtoken());
        //get the item ID of the first element, it should match what we inserted
        assertEquals(eventAllResponse.getData().get(0).getEventID(), event1.getEventID());

        // insert another event with the same username
        Event event2 = generateData.generateRandomEventWithUsername(user.getUsername());
        addEvent(event2);
        //There should now be 2 items inside of the response
        eventAllResponse = (EventAllResponse) eventAllService.eventAll(authToken.getAuthtoken());
        assertEquals(eventAllResponse.getData().size(), 2);

    }





}