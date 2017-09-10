package guru.bonacci.oogway.oracle.client;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.oogway.shareddomain.GemCarrier;
import guru.bonacci.oogway.shareddomain.IGem;

/**
 * Talks to the Oracle via REST
 */
@Component
public class OracleClient {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;

	private final String serviceUrl;

	public OracleClient(@Value("${oracle.service.application.name}") String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl.trim() : "http://" + serviceUrl.trim();
	}

	public Optional<IGem> consult(String searchString) {
		return consult(searchString, null);
	}

	public Optional<IGem> consult(String searchString, String by) {
		logger.info("Oracle consultation:  '" + searchString + "'");

		String params = "?q={searchString}";
		if (by != null)
			params += "&by={by}";
		
		IGem gem = null; 
		try {
			gem = restTemplate.getForObject(serviceUrl + "/gems" + params, GemCarrier.class, searchString, by);
		} catch(Exception ise) {
			logger.error("Help!!! Can't reach the oracle...", ise);	
		}
		return Optional.ofNullable(gem);
	}
	
	public IGem findRandom() {
		logger.info("find me a random Gem");

		IGem gem = null; 
		try {
			gem = restTemplate.getForObject(serviceUrl + "/gems/random", GemCarrier.class);
		} catch(Exception ise) {
			logger.error("Help!!! Can't reach the oracle...", ise);	
		}
		return gem;
	}
}
