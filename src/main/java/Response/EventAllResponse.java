package Response;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class EventAllResponse {
    private ArrayList<JsonObject> data;
    private boolean success;

    public EventAllResponse(ArrayList<JsonObject> data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public ArrayList<JsonObject> getData() {
        return data;
    }

    public void setData(ArrayList<JsonObject> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}