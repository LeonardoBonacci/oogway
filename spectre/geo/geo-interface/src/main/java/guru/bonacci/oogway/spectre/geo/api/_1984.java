package guru.bonacci.oogway.spectre.geo.api;

import static java.lang.String.format;

import java.io.Serializable;

/**
 * Dearest George...
 */
public class _1984 implements Serializable {

	private static final long serialVersionUID = -241744111039377832L;

	private String ip;
	private String message;
	
	public _1984() {}

	public _1984(String ip, String message) {
		this.ip = ip;
		this.message = message;
	}
	
	public String getIP() {
		return ip;
	}

	public void setIP(String ip) {
		this.ip = ip;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
    public String toString() {
        return format("1984[ip='%s', message='%s']", ip, message);
    }
}
