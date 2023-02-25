package Response;

/** Load Response class */
public class LoadResponse extends Response {
    /** constructor of load response based on message and success */
    public LoadResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    /** constructor of load response based on numer of items added */
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
