package Response;

public class FillResponse {

    private String message;
    private boolean success;

    public FillResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

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
