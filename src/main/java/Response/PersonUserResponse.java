package Response;

import Model.Person;
import com.google.gson.JsonObject;

import java.util.ArrayList;
/** Person User response stores the response of the personUser service */
public class PersonUserResponse extends Response {

    /** list of persons associcated with user */
    private ArrayList<Person> data;
    /** constructor sets private fields */
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
