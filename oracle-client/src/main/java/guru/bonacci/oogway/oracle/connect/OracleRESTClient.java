package guru.bonacci.oogway.oracle.connect;

import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.oogway.oracle.api.IGem;

@Service
public class OracleRESTClient {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	//TODO make property
	private final static String SERVICE_URL =  "http://oracle-service";

	public Optional<IGem> consult(String searchString) {
		logger.info("consult() invoked: for " + searchString);

		// getForObject needs an implementation of IGem
		IGem gem = restTemplate.getForObject(SERVICE_URL + "/gems?q={searchString}", RESTGem.class, searchString);
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
	        return format("JMSGem[essence='%s']", essence);
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
