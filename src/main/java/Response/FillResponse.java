package Response;
/** Fill response class */
public class FillResponse extends Response {

    /** constructor of fill response based on message and success */
    public FillResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
    /** constructor of fill response based on number of items inserted */
    public FillResponse(int numPersons, int numEvents) {
        this.message = "Successfully added " + numPersons + " persons and " + numEvents + " events to the database";
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
