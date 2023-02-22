package Model.Data;

import Dao.DataAccessException;
import Dao.EventDao;
import Dao.PersonDao;
import Model.Event;
import Model.Person;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.UUID;

public class GenerateFamilyTree {

    LoadData data;

    String username;
    Connection connection;
    public GenerateFamilyTree(String username, Connection givenConnection){
        this.username = username;
        this.connection = givenConnection;
        try {
            data = new LoadData();
        } catch (FileNotFoundException e) {
            System.out.printf("file not found in load data");
            e.printStackTrace();
        }
    }

    public void generateTree(int generations){
        try {
            Person p = generatePerson("f", 4);
        } catch (DataAccessException e) {
            e.printStackTrace();
            System.out.printf("error while inserting into database");
        }
    }
    Person generatePerson(String gender, int generations) throws DataAccessException {

        Person mother = null;
        Person father = null;

        if (generations > 1){
            mother = generatePerson("f", generations-1);
            father = generatePerson("m", generations-1);

            father.setSpouseID(mother.getPersonID());
            mother.setSpouseID(father.getPersonID());

            Event fatherMarriage = generateEvent(father.getPersonID(), 1900, "marriage");
            (new EventDao(connection)).insertEvent(fatherMarriage);
            Event motherMarriage = fatherMarriage;
            fatherMarriage.setPersonID(mother.getPersonID());
            fatherMarriage.setEventID(getRandomID());

            (new EventDao(connection)).insertEvent(motherMarriage);

        }

        Person person;
        if (mother != null && father != null){
            person = generatePerson(gender, mother.getPersonID(), father.getPersonID(), null);
        } else {
            person = generatePerson(gender, null, null, null);
        }
        new PersonDao(connection).insertPerson(person);

        Event birthEvent = generateEvent(person.getPersonID(), 1900, "birth");
        Event deathEvent = generateEvent(person.getPersonID(), 1900, "death");

        new EventDao(connection).insertEvent(birthEvent);
        new EventDao(connection).insertEvent(deathEvent);


        return person;
    }


    private Event generateEvent(String givenPersonID, int givenYear, String givenEventType){
        String eventID = getRandomID();
        String associatedUsername = this.username;
        String personID = givenPersonID;

        Location randomLocation = data.getLocationData().getRandomItem();
        float latitude = randomLocation.getLatitude();
        float longitude = randomLocation.getLongitude();
        String country = randomLocation.getCountry();
        String city = randomLocation.getCity();
        String eventType = givenEventType;

        int year = givenYear;
        return new Event(eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year);

    }

    private Person generatePerson(String givenGender, String givenFatherID, String givenMotherID, String givenSpouseID){
        String personID = getRandomID();
        String associatedUsername = username;
        String firstName;
        if (givenGender == "f"){
            firstName = data.fNamesData.getRandomItem();
        } else {
            firstName = data.mNamesData.getRandomItem();
        }

        String lastName = data.sNamesData.getRandomItem();
        String gender = givenGender;
        String fatherID = givenFatherID;
        String motherID = givenMotherID;
        String spouseID = givenSpouseID;

        Person p = new Person(personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID);
        return p;
    }

    private String getRandomID(){
        return UUID.randomUUID().toString();
    }
}
