package guru.bonacci.oogway.web.clients;

import static java.util.Optional.ofNullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.shareddomain.GemCarrier;

@Component
public class OracleClient {

	@Autowired
	private RefreshScope refreshScope;
	
	@Autowired
	private ConfigurableEnvironment env;

	@Autowired
	private OAuth2RestTemplate restTemplate;

	int i = 0;

	//TODO to aspect
	public void prepare() {
		//determine new value
		Map<String, Object> s = new HashMap<>();
		i++;
		if (i % 2 == 0)
			s.put("pw", "password");
		else 
			s.put("pw", "passwordsssss");

		MapPropertySource newSource = new MapPropertySource("mytemp", s);
		MutablePropertySources sources = env.getPropertySources();
		
		if (sources.contains("mytemp")) {
			sources.replace("mytemp", newSource);
		} else {
	        sources.addFirst(newSource);
		}
		refreshScope.refresh("client");
	}
	

	public Optional<GemCarrier> consult(String q, String author) {
		prepare();
		return ofNullable(restTemplate.getForObject("http://oracle-service:4444/oracle/gems?q=" + q, GemCarrier.class));
	}
}

