package Response;

public class UserRegisterResponse extends Response {
    private String authtoken;
    private String username;
    private String personID;

    public UserRegisterResponse(String authToken, String username, String personID, boolean success) {
        this.authtoken = authToken;
        this.username = username;
        this.personID = personID;
        this.success = success;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
