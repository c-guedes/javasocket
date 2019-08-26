import java.io.Serializable;

public class ServerResponse implements Serializable {
    private int responseCode;
    private String message;

    @Override
    public String toString() {
        return "ServerResponse{" +
                "responseCode=" + responseCode +
                ", message='" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    ServerResponse(int responseCode) {
        this.responseCode = responseCode;
    }

    public ServerResponse(int responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }
}
