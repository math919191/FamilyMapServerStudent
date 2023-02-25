package Request;

/** User login request */
public class UserLoginRequest {
    /** given username */
    private String username;
    /** given password */
    private String password;
    /** User login request constructor that sets the private fields */
    public UserLoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
