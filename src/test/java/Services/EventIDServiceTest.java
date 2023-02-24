package Services;

import Dao.DataAccessException;
import Model.AuthToken;
import Model.Data.GenerateData;
import Model.Event;
import Model.User;
import Response.Response;
import Response.ErrorResponse;
import Response.EventIDResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventIDServiceTest extends TestHelperFunctions {
    GenerateData generateData;
    EventIDService eventIDService;
    AuthToken authToken;
    User user;
    Event event1;
    Event event2;
    @BeforeEach
    void setUp() throws DataAccessException {

        //clear all the data for a fresh start
        new ClearService().clear();

        //generate data class to be able to get data
        generateData = new GenerateData();
        eventIDService = new EventIDService();

        //create dummy data
        user = generateData.generateRandomUser();
        authToken = generateData.generateRandomAuthTokenWithUsername(user.getUsername());
        event1 = generateData.generateRandomEventWithUsername(user.getUsername());
        event2 = generateData.generateRandomEventWithUsername("fake username");

        addUser(user);
        addAuthToken(authToken);
        addEvent(event1);
        addEvent(event2);

    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    void EventIDFail(){

        // give a bad event ID
        Response response = eventIDService.getEventFromEventID("bad event id", authToken.getAuthtoken());
        assertEquals(response.getClass(), ErrorResponse.class);
        ErrorResponse errorResponse = (ErrorResponse) response;
        assertTrue(errorResponse.getMessage().contains("invalid eventID"));

        //give a bad authtoken
        response = eventIDService.getEventFromEventID(event1.getEventID(), "bad authtoken");
        assertEquals(response.getClass(), ErrorResponse.class);
        errorResponse = (ErrorResponse) response;
        assertTrue(errorResponse.getMessage().contains("invalid authToken"));

        //give valid event ID and a valid authtoken, but make them not match
        response = eventIDService.getEventFromEventID(event2.getEventID(), authToken.getAuthtoken());
        assertEquals(response.getClass(), ErrorResponse.class);
        errorResponse = (ErrorResponse) response;
        assertTrue(errorResponse.getMessage().contains("do not match"));


    }



    @Test
    void EventIDPass() throws DataAccessException {

        Response response = eventIDService.getEventFromEventID(event1.getEventID(), authToken.getAuthtoken());

        //check returns a valid response
        assertEquals(response.getClass(), EventIDResponse.class);

        //cast to event EventIDResponse
        EventIDResponse EventIDResponse = (EventIDResponse) response;

        //We should get the same event back
        assertEquals(EventIDResponse.getEventID(), event1.getEventID());

    }


}