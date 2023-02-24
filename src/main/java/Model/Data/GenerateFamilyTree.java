package Model.Data;

import Dao.DataAccessException;
import Dao.EventDao;
import Dao.PersonDao;
import Dao.UserDao;
import Model.Event;
import Model.Person;
import Model.User;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;
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
            generatePerson(getGender(username), generations);
            insertUser(generations);


        } catch (DataAccessException e) {
            e.printStackTrace();
            System.out.printf("error while inserting into database");
        }
    }



    Person generatePerson(String gender, int generations) throws DataAccessException {

        Person mother = null;
        Person father = null;

        if (generations >= 1){
            mother = generatePerson("f", generations-1);
            father = generatePerson("m", generations-1);

            father.setSpouseID(mother.getPersonID());
            mother.setSpouseID(father.getPersonID());

            insertPerson(father);
            insertPerson(mother);


            Event marriage = generateMarriageEvent(father.getPersonID(), getYear("marriage", generations));
            //inserts father marriage event
            insertEvent(marriage);

            //edits event for mother
            marriage.setPersonID(mother.getPersonID());
            marriage.setEventID(getRandomID());
            //inserts mother marriage event
            insertEvent(marriage);

        }



        Person person;
        if (mother != null && father != null){
            person = generateOnePerson(gender, mother.getPersonID(), father.getPersonID(), null);
        } else {
            person = generateOnePerson(gender, null, null, null);
        }

        Event birth = generateEvent(person.getPersonID(), getYear("birth", generations), "birth");
        Event death = generateEvent(person.getPersonID(), getYear("death", generations), "death");
        new EventDao(connection).insertEvent(death);
        new EventDao(connection).insertEvent(birth);


        return person;
    }


    private void insertUser(int generations) throws DataAccessException {
        //If the number of generations is 1, then they don't have parents/
        if (generations == 0){
            generateUserPerson(username, null, null, null);
            return;
        }

        //find the birth events of the parents based on the year
        ArrayList<Event> birthEvents = new EventDao(connection).findAllEventsWithYear(1970);
        String fatherID;
        String motherID;
        String id1 = birthEvents.get(0).getPersonID();
        String id2 = birthEvents.get(1).getPersonID();

        Person person1 = new PersonDao(connection).findPerson(id1);
        if (person1.getGender() == "m"){
            fatherID = id1;
            motherID = id2;
        } else {
            fatherID = id2;
            motherID = id1;
        }

        //generates the person based on given info and inserts in to db
        generateUserPerson(username, fatherID, motherID, null);

    }

    private int getYear(String eventType, int genNum){
        genNum = genNum + 1;
        //assuming all individuals in the same generation were born and died in the same year
        // everyone died at 50
        // everyone was married at 25
    //                       |        born 2000 died 2050 married 2025 gen 0
    //                    /    \      born 1970 died 2020 married 1995 gen 1
    //                   /\    /\     born 1940 died 1990 married 1965 gen 2
    //                  /\/\  /\/\    born 1910 died 1960 married 1935 gen 3
    //                 /\/\/\/\/\/\   born 1880 died 1930 married 1905 gen 4
    //                /\/\/\/\/\/\/\  born 1850 died 1900 married 1875 gen 5

    if (eventType == "birth"){
        return 2000 - genNum*30;
    } else if (eventType == "death"){
        return 2050 - genNum*30;
    } else if (eventType == "marriage"){
        return 2025 - genNum*30;
    } else { // they'll be 30 for any other events happening in their life.
        return 2030 - genNum*30;
    }


    }


    private Event generateMarriageEvent(String givenPersonID, int givenYear) throws DataAccessException {
        String eventID = getRandomID();
        String associatedUsername = this.username;
        String personID = givenPersonID;

        Location randomLocation = data.getLocationData().getRandomItem();
        float latitude = randomLocation.getLatitude();
        float longitude = randomLocation.getLongitude();
        String country = randomLocation.getCountry();
        String city = randomLocation.getCity();
        String eventType = "marriage";

        int year = givenYear;
        Event e = new Event(eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year);

        return e;
    }

    private void insertEvent(Event e) throws DataAccessException {
        new EventDao(connection).insertEvent(e);
    }
    private void insertPerson(Person p) throws DataAccessException {
        new PersonDao(connection).insertPerson(p);
    }

    private Event generateEvent(String givenPersonID, int givenYear, String givenEventType) throws DataAccessException {
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
        Event e = new Event(eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year);

        return e;
    }

    private Person generateOnePerson(String givenGender, String givenFatherID, String givenMotherID, String givenSpouseID) throws DataAccessException {
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

    private Person generateUserPerson(String username, String givenFatherID, String givenMotherID, String givenSpouseID) throws DataAccessException {
        User user = new UserDao(connection).findUserFromUserName(username);
        String personID = user.getPersonID();
        String associatedUsername = username;
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String gender = user.getGender();
        String fatherID = givenFatherID;
        String motherID = givenMotherID;
        String spouseID = givenSpouseID;

        Person p = new Person(personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID);
        new PersonDao(connection).insertPerson(p);

        return p;

    }

    private String getGender(String username) throws DataAccessException {
        User user = new UserDao(connection).findUserFromUserName(username);

        return user.getGender();
    }

    private String getRandomID(){
        return UUID.randomUUID().toString();
    }
}
