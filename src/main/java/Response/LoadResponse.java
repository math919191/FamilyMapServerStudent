package Response;

public class LoadResponse {
    private String message;
    private boolean success;


    public LoadResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }


    public LoadResponse(int numUsers, int numPersons, int numEvents) {
        this.message = String.format("Successfully added %d users, %d persons, and %d events to the database.", numUsers, numPersons, numEvents);
        this.success = true;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
