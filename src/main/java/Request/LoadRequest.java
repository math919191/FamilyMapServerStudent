package Request;

import Model.Event;
import Model.Person;
import Model.User;
import com.google.gson.JsonObject;

import java.util.ArrayList;
/** Load request */
public class LoadRequest {
    /** list of users */
    private ArrayList<User> users;
    /** list of persons */
    private ArrayList<Person> persons;
    /** list of events */
    private ArrayList<Event> events;

    /** Load request constructor, sets all the private fields */
    public LoadRequest(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }


    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
