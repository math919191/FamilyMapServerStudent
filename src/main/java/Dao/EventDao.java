package Dao;

import Model.AuthToken;
import Model.Event;

import java.util.ArrayList;

public class EventDao {



    /**
     * inserts an event into the database
     * @params event an event
     * */
    void insertEventToken(Event event){}

    /**
     * gets all events
     * @return ArrayList<Event> an array of all the events
     * */
    ArrayList<Event> getAllEvents(){
        return null;
    }


    /**
     * gets an event based on an eventID
     * @params eventID a eventID
     * @return Event the corresponding event to the eventID
     * */
    Event getEvent(String eventID){
        return null;
    }

    /**
     * clears the event table
     * */
    void clear(){}

}
