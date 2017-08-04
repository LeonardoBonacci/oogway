package guru.bonacci.oogway.spectre.geo.service.services;

import java.io.Serializable;

public class Wrapper implements Serializable {

	private static final long serialVersionUID = -241744111039377832L;

	private String uuid;

	public Wrapper() {}

	public Wrapper(String uuid) {
		this.uuid = uuid;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
