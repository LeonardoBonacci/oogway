package guru.bonacci.oogway.jms;

import java.io.Serializable;

/**
 * The smoke signal is one of the oldest forms of long-distance communication.
 * It is a form of visual communication used over long distance. In general
 * smoke signals are used to transmit news, signal danger, or gather people to a
 * common area.
 */
public class SmokeSignal implements Serializable {

	private static final long serialVersionUID = -4448389103738669559L;

	private String message;

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
