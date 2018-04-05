package guru.bonacci.oogway.entrance.security;

import guru.bonacci.oogway.shareddomain.UserInfo;

public class Credentials implements UserInfo {

    private String username;
    
    private String pw;

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
    public String getPw() {
        return pw;
    }

    @Override
    public void setPw(String pw) {
        this.pw = pw;
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


