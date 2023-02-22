package Response;

import Model.Event;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class EventAllResponse extends Response {
    private ArrayList<Event> data;

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
