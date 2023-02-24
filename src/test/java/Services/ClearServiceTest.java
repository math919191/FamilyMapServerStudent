package Services;

import Dao.DataAccessException;
import Dao.Database;
import Dao.EventDao;
import Model.Data.GenerateData;
import Model.Event;
import Request.LoadRequest;
import Response.Response;
import Response.ClearResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest extends TestHelperFunctions {

    private ClearService clearService;
    private LoadRequest loadRequest;
    private Event testEvent;

    @BeforeEach
    void setUp() throws DataAccessException {
        clearService = new ClearService();

        //clear everything before trying to run the tests
        clearService.clear();

        GenerateData generateData = new GenerateData();
        loadRequest = generateData.generateLoadRequest();
        testEvent = generateData.generateRandomEvent();
    }

    @AfterEach
    public void tearDown() {}


    @Test
    void clearServicePass() throws DataAccessException {

        addEvent(testEvent);
        //checks items were actually added
        //checks there is one item, and it matches
        assertEquals(findEvents().size(), 1);
        assertEquals(findEvents().get(0).getEventID(), testEvent.getEventID());

        Response r = clearService.clear();

        //verify no events are return
        assertEquals(findEvents().size(), 0);
        //verify that the response for clear is not an error.
        assertEquals(r.getClass(), ClearResponse.class);
    }

    @Test
    void clearServicePass2() throws DataAccessException {
        addEvent(testEvent);
        //checks items were actually added
        //checks there is one item, and it matches
        assertEquals(findEvents().size(), 1);
        assertEquals(findEvents().get(0).getEventID(), testEvent.getEventID());

        //clearing twice shouldn't throw any errors.
        clearService.clear();
        clearService.clear();

        //verify no events are return
        assertEquals(findEvents().size(), 0);

    }

}