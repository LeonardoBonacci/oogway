package guru.bonacci.oogway.shareddomain;

public interface UserInfo {

    String getUsername(); 

	void setUsername(String username);

    String getPw();

	void setPw(String pw);

    String getApiKey();
    
	void setApiKey(String apiKey);
}


