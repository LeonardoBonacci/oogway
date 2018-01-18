package guru.bonacci.oogway.oracle.client;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import guru.bonacci.oogway.shareddomain.GemCarrier;

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

	@HystrixCommand(fallbackMethod = "fallbackConsult")
	public Optional<GemCarrier> consult(String searchString, String by) {
		logger.info("Oracle consultation:  '" + searchString + "'");

		String params = "?q={searchString}";
		if (by != null)
			params += "&by={by}";
		
		GemCarrier gem = restTemplate.getForObject(serviceUrl + "/gems" + params, GemCarrier.class, searchString, by);
		return Optional.ofNullable(gem);
	}

	public Optional<GemCarrier> fallbackConsult(String searchString, String by, Throwable t) {
        return fallback(t);
    }

	@HystrixCommand(fallbackMethod = "fallback",
					commandProperties = {
							@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500")
					})
	public Optional<GemCarrier> random() {
		logger.info("find me a random Gem");

		GemCarrier gem = restTemplate.getForObject(serviceUrl + "/gems/random", GemCarrier.class);
		return Optional.ofNullable(gem);
	}
	
    /**
     * General fallback method
     * @param t
     * @return
     */
    public Optional<GemCarrier> fallback(Throwable t) {
        logger.error("Help!!! Can't reach the oracle...", t);    
        return Optional.empty();
    }
}
