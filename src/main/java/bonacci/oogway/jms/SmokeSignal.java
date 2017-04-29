package bonacci.oogway.jms;

public class SmokeSignal {

    private String message;

    public SmokeSignal() {}

    public SmokeSignal(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("SmokeSignal{message=%s}", getMessage());
    }
}
