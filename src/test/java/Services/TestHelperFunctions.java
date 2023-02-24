package Services;

import Dao.*;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;

import java.sql.Connection;
import java.util.ArrayList;

public class TestHelperFunctions {
    //helper functions
    void addEvent(Event e) throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();

        new EventDao(conn).insertEvent(e);

        db.closeConnection(true);
    }
    void addUser(User e) throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();

        new UserDao(conn).insertUser(e);

        db.closeConnection(true);
    }

    void addAuthToken(AuthToken e) throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();

        new AuthtokenDao(conn).insertAuthToken(e);

        db.closeConnection(true);
    }

    void addPerson(Person e) throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();

        new PersonDao(conn).insertPerson(e);

        db.closeConnection(true);
    }

    ArrayList<Event> findEvents() throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();

        ArrayList<Event> e = new EventDao(conn).findAllEvents();
        db.closeConnection(true);

        return e;
    }

    ArrayList<Person> findPersons(String username) throws DataAccessException {
        Database db = new Database();
        Connection conn = db.getConnection();

        ArrayList<Person> e = new PersonDao(conn).findPersons(username);
        db.closeConnection(true);

        return e;
    }
}
