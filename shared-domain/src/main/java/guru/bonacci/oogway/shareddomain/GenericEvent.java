package guru.bonacci.oogway.shareddomain;

import java.io.Serializable;

public class GenericEvent implements Serializable {

	private static final long serialVersionUID = -241744111039377832L;

	private String content;

	public GenericEvent() {}

	public GenericEvent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
