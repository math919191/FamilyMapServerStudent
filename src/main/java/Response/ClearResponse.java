package Response;
/** response for the clear */
public class ClearResponse extends Response {

    /** Clear Response constructor sets private fields */
    public ClearResponse(String message, boolean success) {

        this.message = message;
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
