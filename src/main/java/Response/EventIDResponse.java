package Response;
/** Event ID response */
public class EventIDResponse extends Response {
    /** Associated username of the event */
    private String associatedUsername;
    /** Associated eventID of the event*/
    private String eventID;
    /** Associated Person ID of the event */
    private String personID;
    /** Latitude location of the event */
    private Float latitude;
    /** Longitude location of the event */
    private Float longitude;
    /** country where the event happened */
    private String country;
    /** city where the event happened */
    private String city;
    /** type of the event */
    private String eventType;
    /** Where the event happened */
    private int year;
    /** Constructor for the  EventIDResponse sets the private fields */

    public EventIDResponse(String associatedUsername, String eventID, String personID, Float latitude, Float longitude, String country, String city, String eventType, int year, boolean success) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.success = success;
        this.message = "event ID response message";
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
