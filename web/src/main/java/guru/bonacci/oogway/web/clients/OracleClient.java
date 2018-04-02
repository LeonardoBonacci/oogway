package guru.bonacci.oogway.web.clients;

import static java.util.Optional.ofNullable;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.shareddomain.GemCarrier;

@Component
public class OracleClient {

	@Autowired
	private OAuth2RestTemplate restTemplate;

	@OracleRequestInterceptor
	public Optional<GemCarrier> consult(String q, String author) {
		return ofNullable(restTemplate.getForObject("http://oracle-service:4444/oracle/gems?q=" + q, GemCarrier.class));
	}
}

