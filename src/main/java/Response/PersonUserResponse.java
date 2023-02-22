package Response;

import Model.Person;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class PersonUserResponse extends Response {

    private ArrayList<Person> data;

    public PersonUserResponse(ArrayList<Person> data, boolean success) {
        this.data = data;
        this.success = success;
        this.message = "person user response message";
    }

    public ArrayList<Person> getData() {
        return data;
    }

    public void setData(ArrayList<Person> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
