package Services;

import Dao.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Response.ErrorResponse;
import Response.LoadResponse;
import Response.Response;

import java.sql.Connection;
import java.util.ArrayList;

public class LoadService {

    /**
     * Clears all data from the database (just like the /clear API)
     * Loads the user, person, and event data from the request body into the database.
     *
     * @param loadRequest a loadRequest
     * @return LoadResponse a loadResponse
     * */

    public Response load(LoadRequest loadRequest){
        Database db = new Database();
        try {
            db.openConnection();
            //calls clear service
            new ClearService().clearGivenConnection(db.getConnection());

            //loops through persons and adds them to the database
            ArrayList<Person> persons = loadRequest.getPersons();
            addPersonsToDatabase(persons, db.getConnection());
            int numPersonsAdded = (persons == null) ? 0 : persons.size();


            //loops through users and adds them to the database
            ArrayList<User> users = loadRequest.getUsers();
            addUsersToDatabase(users, db.getConnection());
            int numUsersAdded = (users == null) ? 0 : users.size();

            //loops through events and adds them to the database
            ArrayList<Event> events = loadRequest.getEvents();
            addEventsToDatabase(events, db.getConnection());
            int numEventsAdded = (events == null) ? 0 : events.size();


            db.closeConnection(true);


            //creates a LoadResponse object and returns
            LoadResponse response = new LoadResponse(numPersonsAdded, numUsersAdded, numEventsAdded);
            return response;

        } catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            ErrorResponse result = new ErrorResponse("Load service failed", false);
            return result;

        }
    }

    private void addEventsToDatabase(ArrayList<Event> events, Connection connection) throws DataAccessException {
        if (events != null){

            EventDao eventDao = new EventDao(connection);
            for (Event e : events){
                eventDao.insertEvent(e);
            }
        }
    }

    private void addUsersToDatabase(ArrayList<User> users, Connection connection) throws DataAccessException {
        if (users != null){

            UserDao userDao = new UserDao(connection);
            for (User u : users){
                userDao.insertUser(u);
            }
        }
    }

    private void addPersonsToDatabase(ArrayList<Person> persons, Connection connection) throws DataAccessException {
        if (persons != null){
            PersonDao personDao = new PersonDao(connection);
            for (Person p : persons){
                personDao.insertPerson(p);
            }

        }
    }


}
