package Services;

import Response.EventIDResponse;

public class EventID {

    /**
     * Returns the single Event object with the specified ID (if the event is associated with the current user).
     * The current user is determined by the provided authtoken.
     *
     * @param eventID the eventID
     * @return EventIDResponse
     * */

    EventIDResponse eventID(String eventID){
        //would call eventDao.findEvent(eventID)
        return null;
    }
}
