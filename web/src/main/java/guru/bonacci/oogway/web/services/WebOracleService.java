package guru.bonacci.oogway.web.services;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.oogway.commons.Gem;

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
public class WebOracleService {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	protected String serviceUrl =  "http://oracle";

	/**
	 * The RestTemplate works because it uses a custom request-factory that uses
	 * Ribbon to look-up the service to use. This method simply exists to show
	 * this.
	 */
	@PostConstruct
	public void demoOnly() {
		// Can't do this in the constructor because the RestTemplate injection
		// happens afterwards.
		logger.warn("The RestTemplate request factory is "
				+ restTemplate.getRequestFactory().getClass());
	}
	
	public Optional<Gem> consult(String searchString) {
		logger.info("consult() invoked: for " + searchString);

		Gem gem = restTemplate.getForObject(serviceUrl + "/gems/{query}", Gem.class, searchString);
		return Optional.ofNullable(gem);
	}
}
