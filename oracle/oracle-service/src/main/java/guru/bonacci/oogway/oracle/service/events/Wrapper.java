package guru.bonacci.oogway.oracle.service.events;

import java.io.Serializable;

public class Wrapper implements Serializable {

	private static final long serialVersionUID = -241744111039377832L;

	private String content;

	public Wrapper() {}

	public Wrapper(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
