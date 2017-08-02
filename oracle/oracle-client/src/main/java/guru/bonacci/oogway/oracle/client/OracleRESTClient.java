package guru.bonacci.oogway.oracle.client;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.oogway.oracle.api.IGem;

/**
 * Talks to the Oracle via REST
 */
@Component
public class OracleRESTClient {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	private final String serviceUrl;

	public OracleRESTClient(@Value("${oracle.service.application.name}") String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl.trim() : "http://" + serviceUrl.trim();
	}
	
	public Optional<IGem> consult(String searchString, String by) {
		logger.info("Oracle consultation:  '" + searchString + "'");

		IGem gem = restTemplate.getForObject(serviceUrl + "/gems?q={searchString}&by={by}", GemDataCarrier.class, searchString, by);
		return Optional.ofNullable(gem);
	}
}
