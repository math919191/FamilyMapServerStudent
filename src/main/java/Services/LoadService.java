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
            addPersonsToDatabase(loadRequest.getPersons(), db.getConnection());
            int numPersonsAdded = loadRequest.getPersons().size();

            //loops through users and adds them to the database
            addUsersToDatabase(loadRequest.getUsers(), db.getConnection());
            int numUsersAdded = loadRequest.getUsers().size();

            //loops through events and adds them to the database
            addEventsToDatabase(loadRequest.getEvents(), db.getConnection());
            int numEventsAdded = loadRequest.getEvents().size();


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

        EventDao eventDao = new EventDao(connection);
        for (Event e : events){
            eventDao.insertEvent(e);
        }
    }

    private void addUsersToDatabase(ArrayList<User> users, Connection connection) throws DataAccessException {
        UserDao userDao = new UserDao(connection);
        for (User u : users){
            userDao.insertUser(u);
        }
    }

    private void addPersonsToDatabase(ArrayList<Person> persons, Connection connection) throws DataAccessException {
        PersonDao personDao = new PersonDao(connection);
        for (Person p : persons){
            personDao.insertPerson(p);
        }
    }


}
