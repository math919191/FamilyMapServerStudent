package Response;

public class UserLoginResponse {
    private String authToken;
    private String username;
    private String personID;
    private boolean success;

    public UserLoginResponse(String authToken, String username, String personID, boolean success) {
        this.authToken = authToken;
        this.username = username;
        this.personID = personID;
        this.success = success;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
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
