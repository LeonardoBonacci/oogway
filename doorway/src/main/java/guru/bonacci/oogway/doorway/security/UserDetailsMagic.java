package guru.bonacci.oogway.doorway.security;


import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsMagic {

	public Credentials userDetailsRepository(String apikey) {
		// this could be as advanced/complicated as desired...
		String password = apikey;
		String user = StringUtils.reverse(password);
		return Credentials.builder().username(user).password(password).build();
	}
}
