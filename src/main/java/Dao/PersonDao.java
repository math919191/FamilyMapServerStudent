package Dao;

import Model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonDao {

    private final Connection conn;
    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * inserts a person into the database
     * @param person a person
     * */
    public void insertPerson(Person person) throws DataAccessException {
        String sql = "INSERT INTO Persons (personID, associatedUsername, firstName, lastName, " +
                "gender, fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a person into the database");
        }
    }

    /**
     * gets a person based on personID
     * @param personID a personID
     * @return Person a person
     * */
    public Person findPerson(String personID) throws DataAccessException {
        Person person;
        ResultSet rs;
        String sql = "SELECT * FROM Persons WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(
                        rs.getString("personID"),
                        rs.getString("associatedUsername"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("gender"),
                        rs.getString("fatherID"),
                        rs.getString("motherID"),
                        rs.getString("spouseID")
                        );

                return person;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a Person in the database");
        }

    }

    /**
     * gets all family members of a user
     * @param associatedUsername associatedUsername
     * @return people list of family members
     * */
    public ArrayList<Person> findPersons(String associatedUsername) throws DataAccessException {
        ArrayList<Person> people = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM Persons WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Person person = new Person(
                        rs.getString("personID"),
                        rs.getString("associatedUsername"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("gender"),
                        rs.getString("fatherID"),
                        rs.getString("motherID"),
                        rs.getString("spouseID")
                );
                people.add(person);
            }

            return people;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding all people associated with username in db");
        }
    }


    public void clearWithUsername(String username) throws DataAccessException {
        String sql = "DELETE FROM Persons WHERE associatedUsername =?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table of user's data");
        }
    }

    /**
     * clears the person table
     * */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Persons";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }

}
