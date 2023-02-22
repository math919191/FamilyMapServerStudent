package Services;

import Dao.AuthtokenDao;
import Dao.Database;
import Dao.EventDao;
import Model.AuthToken;
import Model.Event;
import Response.ErrorResponse;
import Response.EventAllResponse;
import Response.Response;

import java.util.ArrayList;

public class EventAllService {

    /**
     * Returns ALL events for ALL family members of the current user.
     * The current user is determined from the provided auth token.
     *
     * @return EventResponse
     * */

    public Response eventAll(String authToken){
        Database db = new Database();
        try {
            db.openConnection();

            //get username from authtoken
            //get the username of the person by using the authtoken
            AuthtokenDao authtokenDao = new AuthtokenDao(db.getConnection());
            AuthToken authToken1 = authtokenDao.findUserName(authToken);
            String username = authToken1.getUsername();


            //get all the events with the associated username
            EventDao eventDao = new EventDao(db.getConnection());
            ArrayList<Event> events = eventDao.findAllEventsWithUsername(username);

            db.closeConnection(false);

            //create a response and send it back
            EventAllResponse response = new EventAllResponse(events, true);

            return response;

        } catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            ErrorResponse result = new ErrorResponse("Person User service failed", false);
            return result;

        }
    }

}
