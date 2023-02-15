package Request;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class LoadRequest {

    private ArrayList<JsonObject> users;
    private ArrayList<JsonObject> persons;
    private ArrayList<JsonObject> events;


    public LoadRequest(ArrayList<JsonObject> users, ArrayList<JsonObject> persons, ArrayList<JsonObject> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }


    public ArrayList<JsonObject> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<JsonObject> users) {
        this.users = users;
    }

    public ArrayList<JsonObject> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<JsonObject> persons) {
        this.persons = persons;
    }

    public ArrayList<JsonObject> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<JsonObject> events) {
        this.events = events;
    }
}
