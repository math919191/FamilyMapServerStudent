package Dao;

import Model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EventDaoTest {

    private Database db;
    private Event bestEvent;
    private Event worstEvent;

    private EventDao eDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        // Here we can set up any classes or variables we will need for each test
        // lets create a new instance of the Database class
        db = new Database();
        // and a new event with random data
        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        worstEvent = new Event("Broken_91", "Gale", "Gale123A",
                135.9f, 241.1f, "Sweden", "Stockholm",
                "Broken Leg", 2020);

        // Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        eDao = new EventDao(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        eDao.clear();
    }

    @AfterEach
    public void tearDown() {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        db.closeConnection(false);
    }

    @Test
    void insertEventPass() throws DataAccessException {
        // Start by inserting an event into the database.
        eDao.insertEvent(bestEvent);
        // Let's use a find method to get the event that we just put in back out.
        Event compareTest = eDao.findEvent(bestEvent.getEventID());
        // First lets see if our find method found anything at all. If it did then we know that we got
        // something back from our database.
        assertNotNull(compareTest);
        // Now lets make sure that what we put in is the same as what we got out. If this
        // passes then we know that our insert did put something in, and that it didn't change the
        // data in any way.
        // This assertion works by calling the equals method in the Event class.
        assertEquals(bestEvent, compareTest);
    }

    @Test
    void insertEventFail() throws DataAccessException {
        // Let's do this test again, but this time lets try to make it fail.
        // If we call the method the first time the event will be inserted successfully.
        eDao.insertEvent(bestEvent);

        // However, our sql table is set up so that the column "eventID" must be unique, so trying to insert
        // the same event again will cause the insert method to throw an exception, and we can verify this
        // behavior by using the assertThrows assertion as shown below.

        // Note: This call uses a lambda function. A lambda function runs the code that comes after
        // the "()->", and the assertThrows assertion expects the code that ran to throw an
        // instance of the class in the first parameter, which in this case is a DataAccessException.
        assertThrows(DataAccessException.class, () -> eDao.insertEvent(bestEvent));
    }

    @Test
    void findEventPass() throws DataAccessException {
        eDao.insertEvent(bestEvent);
        Event compareTest = eDao.findEvent("Biking_123A");
        assertEquals(bestEvent, compareTest);

        eDao.insertEvent(worstEvent);
        compareTest = eDao.findEvent("Biking_123A");
        assertEquals(bestEvent, compareTest);

        compareTest = eDao.findEvent("Broken_91");
        assertEquals(worstEvent, compareTest);

    }
    @Test
    void findEventFail() throws DataAccessException {
        Event compareTest = eDao.findEvent("fakeEventID");
        assertNull(compareTest);
    }

    @Test
    void findAllEventsPass() throws DataAccessException {
        eDao.insertEvent(bestEvent);
        eDao.insertEvent(worstEvent);

        //creates an equivalent arrayList by maintaining same order.
        ArrayList<Event> events = new ArrayList<>();
        events.add(bestEvent);
        events.add(worstEvent);

        ArrayList<Event> compareTest = eDao.findAllEvents();

        //verify same number of items
        assertEquals(2, compareTest.size());
        assertEquals(events, compareTest);

    }


    @Test
    void findAllEventsWithYearPass() throws DataAccessException {
        eDao.insertEvent(bestEvent);
        eDao.insertEvent(worstEvent);
        ArrayList<Event> compareTest = eDao.findAllEventsWithYear(bestEvent.getYear());
        assertEquals(compareTest.get(0), bestEvent);
    }

    @Test
    void findAllEventsWithYearFail() throws DataAccessException {
        ArrayList<Event> compareTest = eDao.findAllEventsWithYear(200);
        assertEquals(compareTest.size(), 0);
    }

    @Test
    void clearWithUsernamePass() throws DataAccessException {
        eDao.insertEvent(bestEvent);
        Event compareTest = eDao.findEvent(bestEvent.getEventID());
        assertEquals(bestEvent, compareTest);

        eDao.clearWithUsername(bestEvent.getAssociatedUsername());
        compareTest = eDao.findEvent(bestEvent.getEventID());
        assertNull(compareTest);
    }

    @Test
    void clearWithUsernameFail() throws DataAccessException {
        eDao.insertEvent(bestEvent);

        // Clearing twice shouldn't cause it to fail
        eDao.clearWithUsername(bestEvent.getAssociatedUsername());
        eDao.clearWithUsername(bestEvent.getAssociatedUsername());

        Event compareTest = eDao.findEvent(bestEvent.getEventID());
        assertNull(compareTest);
    }



    @Test
    void clear() throws DataAccessException {

        eDao.clear();
        Event compareTest = eDao.findEvent("fakeEventID");
        assertNull(compareTest);

        eDao.insertEvent(worstEvent);
        eDao.insertEvent(bestEvent);
        eDao.clear();

        compareTest = eDao.findEvent("Biking_123A");
        assertNull(compareTest);

        compareTest = eDao.findEvent("Broken_91");
        assertNull(compareTest);
    }
}