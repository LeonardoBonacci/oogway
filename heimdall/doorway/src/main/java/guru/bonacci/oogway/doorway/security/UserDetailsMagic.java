package guru.bonacci.oogway.doorway.security;

import org.springframework.stereotype.Component;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

@Component
public class UserDetailsMagic {

	public Credentials userDetailsRepository(String apikey) {
		// this could be as advanced/complicated as desired...
		String password = apikey;
		String user = StringUtils.reverse(password);
		return Credentials.builder().username(user).password(password).build();
	}
}
