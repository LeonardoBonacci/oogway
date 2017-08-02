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
public class OracleRESTClient {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	private final String serviceUrl;

	public OracleRESTClient(@Value("${oracle.service.application.name}") String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl.trim() : "http://" + serviceUrl.trim();
	}
	
	public Optional<IGem> consult(String searchString) {
		logger.info("Oracle consultation:  '" + searchString + "'");

		// getForObject needs an implementation of IGem
		IGem gem = restTemplate.getForObject(serviceUrl + "/gems?q={searchString}", RestGem.class, searchString);
		return Optional.ofNullable(gem);
	}
	
	/**
	 * Until jigsaw allows us to hide a class from those who use this library this does the work...
	 */
	static class RestGem implements IGem {

		private String said;

		private String by;

		private String on;

		private String source;

		public RestGem() {
		}

		public RestGem(String said) {
			this.said = said;
		}

		@Override
		public String getSaid() {
			return said;
		}

		@Override
		public void setSaid(String said) {
			this.said = said;
		}

		@Override
		public String getBy() {
			return by;
		}

		@Override
		public void setBy(String by) {
			this.by = by;
		}

		@Override
		public String getOn() {
			return on;
		}

		@Override
		public void setOn(String on) {
			this.on = on;
		}

		@Override
		public String getSource() {
			return source;
		}

		@Override
		public void setSource(String source) {
			this.source = source;
		}

		@Override
	    public String toString() {
	        return format("RestGem[said='%s', by='%s', on='%s']", said, by, on);
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
