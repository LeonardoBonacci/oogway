package bonacci.oogway.jms;

import java.io.Serializable;

public class SmokeSignal implements Serializable {

	private static final long serialVersionUID = -4448389103738669559L;

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
