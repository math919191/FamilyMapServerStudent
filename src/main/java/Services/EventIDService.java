package Services;

import Dao.AuthtokenDao;
import Dao.Database;
import Dao.EventDao;
import Model.AuthToken;
import Model.Event;
import Response.ErrorResponse;
import Response.EventIDResponse;
import Response.Response;

public class EventIDService {

    /**
     * Returns the single Event object with the specified ID (if the event is associated with the current user).
     * The current user is determined by the provided authtoken.
     *
     * @param eventID the eventID
     * @return EventIDResponse
     * */

    public Response getEventFromEventID(String eventID, String givenAuthtoken){
        Database db = new Database();
        try {
            db.openConnection();

            //get the event with the id
            Event event = new EventDao(db.getConnection()).findEvent(eventID);
            if (event == null){
                throw new Exception("invalid eventID bad request");
            }

            //get the username of the person by using the authtoken

            AuthtokenDao authtokenDao = new AuthtokenDao(db.getConnection());
            AuthToken authToken1 = authtokenDao.findUserName(givenAuthtoken);
            if (authToken1 == null){
                throw new Exception("invalid authToken bad request");
            }

            String usernameFromAuthToken = authToken1.getUsername();

            //verify the usernames match
            if (!usernameFromAuthToken.equals(event.getAssociatedUsername())){
                throw new Exception("Username from authToken and Username from event do not match bad request");
            }

            //create a response and send it back
            db.closeConnection(false);

            EventIDResponse response = getEventIDResponseFromEvent(event);
            return response;

        } catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            ErrorResponse result = new ErrorResponse("Event ID service failed" + ex.getMessage(), false);
            return result;

        }

    }

    private EventIDResponse getEventIDResponseFromEvent(Event event) {
        EventIDResponse idResponse = new EventIDResponse(
                event.getAssociatedUsername(),
                event.getEventID(),
                event.getPersonID(),
                event.getLatitude(),
                event.getLongitude(),
                event.getCountry(),
                event.getCity(),
                event.getEventType(),
                event.getYear(),
                true
        );
        return idResponse;

    }
}
