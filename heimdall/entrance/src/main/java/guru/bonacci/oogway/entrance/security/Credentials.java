package guru.bonacci.oogway.entrance.security;

import com.fasterxml.jackson.annotation.JsonIgnore;

import guru.bonacci.oogway.shareddomain.UserInfo;

public class Credentials implements UserInfo {

    private String username;
    
    private String encryptedPassword;

	@JsonIgnore
    private String password;

    private String apiKey;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
	public void setUsername(String username) {
		this.username = username;
	}

    @Override
    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    @Override
    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonIgnore
	public void setPassword(String password) {
		this.password = password;
	}

    @Override
    public String getApiKey() {
        return apiKey;
    }

    @Override
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}


