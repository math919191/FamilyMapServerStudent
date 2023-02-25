package Response;
/** Error response for when things go wrong*/
public class ErrorResponse extends Response {
    /** Error Response constructor sets private fields */
    public ErrorResponse(String message, boolean success) {
        this.message = "Error: " + message;
        this.success = success;
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
