package Model.Data;

import Dao.EventDao;
import Model.Event;
import Model.Person;

import java.io.FileNotFoundException;
import java.util.UUID;

public class GenerateFamilyTree {

    LoadData data;

    String username;
    GenerateFamilyTree(String username){
        this.username = username;
        try {
            data = new LoadData();
        } catch (FileNotFoundException e) {
            System.out.printf("file not found in load data");
            e.printStackTrace();
        }
    }
    Person generatePerson(String gender, int generations){

        Person mother = null;
        Person father = null;

        if (generations > 1){
            mother = generatePerson("f", generations-1);
            father = generatePerson("m", generations-1);

            father.setSpouseID(mother.getPersonID());
            mother.setSpouseID(father.getPersonID());

            Event fatherMarriage = generateMarriageEvent(father.getPersonID());
            //TODO add event to the database
            Event motherMarriage = fatherMarriage;
            fatherMarriage.setPersonID(mother.getPersonID());
            //TODO add event to the database
        }

    }

    private Event generateMarriageEvent(String givenPersonID){

        String eventID = UUID.randomUUID().toString();
        String associatedUsername = this.username;
        String personID = givenPersonID;

        Location randomLocation = data.getLocationData().getRandomItem();
        float latitude = randomLocation.getLatitude();
        float longitude = randomLocation.getLongitude();
        String country = randomLocation.getCountry();
        String city = randomLocation.getCity();
        String eventType = "marriage";

        //TODO fix this year to be different
        int year = 1900;

        return new Event(eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year);

    }

}
