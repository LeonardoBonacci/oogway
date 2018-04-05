package guru.bonacci.oogway.entrance.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.oogway.entrance.security.Credentials;

@Component
public class AuthClient {

	@Autowired
	private RestTemplate restTemplate;

	private String serviceUrl;

	public AuthClient(@Value("${auth.service.url}") String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl.trim() : "http://" + serviceUrl.trim();
	}

	public Credentials user() {
		return restTemplate.getForObject(serviceUrl + "/auth/users/user1", Credentials.class);
	}
}

