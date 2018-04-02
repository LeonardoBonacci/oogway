package guru.bonacci.oogway.web.services;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;

import guru.bonacci.oogway.shareddomain.GemCarrier;
import guru.bonacci.oogway.web.cheaters.Postponer;
import guru.bonacci.oogway.web.clients.OAuth2RestTemplateFactory;
import guru.bonacci.oogway.web.intercept.WatchMe;

/**
 * Tier I is the initial support level responsible for basic customer issues. It
 * is synonymous with first-line support, level 1 support, front-end support,
 * support line 1, and various other headings denoting basic level technical
 * support functions. The first job of a Tier I specialist is to gather the
 * customer’s information and to determine the customer’s issue by analyzing the
 * symptoms and figuring out the underlying problem. When analyzing the
 * symptoms, it is important for the technician to identify what the customer is
 * trying to accomplish so that time is not wasted on "attempting to solve a
 * symptom instead of a problem."
 */
@Service
public class FirstLineSupportService {

	@Autowired
	private RefreshScope refreshScope;
	
	@Autowired
	private OAuth2RestTemplate restTemplate;

	@Autowired
	private Postponer postponer;

	@Autowired
	private ConfigurableEnvironment env;

	int i = 0;
	
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


//        PropertySource source = sources.get("temp");
//        if (source == null)
//        	source = new PropertiesPropertySource("temp");
		refreshScope.refresh("client");
	}
	
	@WatchMe
	public GemCarrier enquire(String q) {
		if (isEmpty(q))
			return new GemCarrier("No question no answer..", "oogway");

		prepare();//TODO to aspect
		Optional<GemCarrier> gem = 
				ofNullable(restTemplate.getForObject("http://oracle-service:4444/oracle/gems?q=" + q, GemCarrier.class));
		return gem.orElse(new GemCarrier(postponer.saySomething(), "oogway"));
	}
}
