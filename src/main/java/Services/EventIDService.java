package Services;

import Response.EventIDResponse;

public class EventIDService {

    /**
     * Returns the single Event object with the specified ID (if the event is associated with the current user).
     * The current user is determined by the provided authtoken.
     *
     * @param eventID the eventID
     * @return EventIDResponse
     * */

    EventIDResponse eventID(String eventID){
        //would call eventDao.findEvent(eventID)
        //would convert response to EventIDResponse
        return null;
    }
}