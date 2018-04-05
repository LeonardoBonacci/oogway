package guru.bonacci.oogway.entrance.security;

public class Credentials {

	private String username;
	private String password;
	
	public Credentials(String u, String p) {
		username = u;
		password = p;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
