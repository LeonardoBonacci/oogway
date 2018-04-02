package guru.bonacci.oogway.entrance.clients;

import static java.util.Optional.ofNullable;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import guru.bonacci.oogway.shareddomain.GemCarrier;

@Component
public class OracleClient {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private RestTemplate restTemplate;

	private final String serviceUrl;

	public OracleClient(@Value("${oracle.service.url}") String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl.trim() : "http://" + serviceUrl.trim();
	}
	
	@OracleRequestInterceptor
	@HystrixCommand(fallbackMethod = "fallback")
	public Optional<GemCarrier> consult(String searchString, String author) {
		String params = "?q={searchString}";
		if (author != null)
			params += "&by={author}";

		return ofNullable(restTemplate.getForObject(serviceUrl + "/oracle/gems" + params, GemCarrier.class, searchString, author));
	}

	public Optional<GemCarrier> fallback(String searchString, String by, Throwable cause) {
        logger.error("Help!!! Can't reach the oracle...", cause);    
        return Optional.empty();
	}
}

