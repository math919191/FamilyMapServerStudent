package Model.Data;

import Dao.AuthtokenDao;
import Dao.DataAccessException;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class GenerateData {

    private LoadData data;
    public GenerateData(){
        try {
            data = new LoadData();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public LoadRequest generateLoadRequest(){
        ArrayList<User> users = new ArrayList<>();
        users.add(generateRandomUser());

        ArrayList<Person> persons = new ArrayList<>();
        persons.add(generateRandomPerson());

        ArrayList<Event> events = new ArrayList<>();
        events.add(generateRandomEvent());


        return new LoadRequest(users, persons, events);
    }

    public Event generateRandomEvent() {


        String eventID = getRandomID();
        String associatedUsername = "fakeUsername";
        String personID = getRandomID();


        Location randomLocation = data.getLocationData().getRandomItem();
        float latitude = randomLocation.getLatitude();
        float longitude = randomLocation.getLongitude();
        String country = randomLocation.getCountry();
        String city = randomLocation.getCity();
        String eventType = "fakeEventType";

        int year = 1900;
        Event e = new Event(eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year);

        return e;
    }

    public Event generateRandomEventWithUsername(String username){
        Event e = generateRandomEvent();
        e.setAssociatedUsername(username);
        return e;
    }

    public Person generateRandomPerson() {

        String personID = getRandomID();
        String associatedUsername = "fakeUsername";

        String gender = getRandomGender();
        String firstName = getRandomNameGivenGender(gender);


        String lastName = data.sNamesData.getRandomItem();

        String fatherID = getRandomID();
        String motherID = getRandomID();
        String spouseID = getRandomID();

        Person p = new Person(personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID);

        return p;
    }

    public Person generateRandomPersonWithUsername(String username){
        Person p = generateRandomPerson();
        p.setAssociatedUsername(username);
        return p;
    }


    public User generateRandomUser(){
        String gender = getRandomGender();
        String firstName = getRandomNameGivenGender(gender);
        String username = firstName + getRandomNumber(1000);
        String password = "password" + username;
        String email = username + "@example.com";
        String lastName = data.sNamesData.getRandomItem();

        String personID = getRandomID();

        return new User(username,password, email, firstName, lastName, gender, personID);
    }

    public AuthToken generateRandomAuthToken(){

        String authToken = getRandomID();
        String username = getRandomNameGivenGender(getRandomGender()) + getRandomNumber(1000);

        return new AuthToken(authToken, username);
    }

    public AuthToken generateRandomAuthTokenWithUsername(String username){
        AuthToken a = generateRandomAuthToken();
        a.setUsername(username);
        return a;
    }


    private String getRandomNameGivenGender(String gender) {
        String firstName;
        if (gender == "f"){
            firstName = data.fNamesData.getRandomItem();
        } else {
            firstName = data.mNamesData.getRandomItem();
        }
        return firstName;
    }

    private String getRandomGender() {
        return getRandomNumber(2) == 0 ? "m" : "f";
    }





    private String getRandomID(){
        return UUID.randomUUID().toString();
    }

    private int getRandomNumber(int upperBound){
        Random rand = new Random();
        return rand.nextInt(upperBound);
    }
}
