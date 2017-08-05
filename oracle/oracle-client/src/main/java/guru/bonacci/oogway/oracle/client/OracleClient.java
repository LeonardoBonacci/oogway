package guru.bonacci.oogway.oracle.client;

import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
public class OracleClient {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	private final String serviceUrl;

	public OracleClient(@Value("${oracle.service.application.name}") String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl.trim() : "http://" + serviceUrl.trim();
	}
	
	public Optional<IGem> consult(String searchString) {
		logger.info("Oracle consultation:  '" + searchString + "'");

		// getForObject needs an implementation of IGem
		IGem gem = restTemplate.getForObject(serviceUrl + "/gems?q={searchString}", RESTGem.class, searchString);
		return Optional.ofNullable(gem);
	}
	
	/**
	 * Until jigsaw allows us to hide a class from those who use this library this does the work...
	 */
	static class RESTGem implements IGem {

		private String essence;

		public RESTGem() {
		}

		public RESTGem(String essence) {
			this.essence = essence;
		}

		@Override
		public String getEssence() {
			return essence;
		}

		@Override
		public void setEssence(String essence) {
			this.essence = essence;
		}
		
		@Override
	    public String toString() {
	        return format("RestGem[essence='%s']", essence);
	    }
		
		@Override
		public boolean equals(Object o) {
		    return EqualsBuilder.reflectionEquals(this, o);
		}
		
		@Override
		public int hashCode() {
		    return HashCodeBuilder.reflectionHashCode(this);
		}
	}
}
