package Response;

import Model.Event;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/** Event All response */
public class EventAllResponse extends Response {
    /** list of Events */
    private ArrayList<Event> data;

    /** Event all response constructor sets private fields */
    public EventAllResponse(ArrayList<Event> data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public ArrayList<Event> getData() {
        return data;
    }

    public void setData(ArrayList<Event> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
