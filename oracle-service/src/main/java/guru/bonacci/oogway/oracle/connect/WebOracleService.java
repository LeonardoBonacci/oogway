package guru.bonacci.oogway.oracle.connect;

import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.oogway.oracle.api.IGem;

@Service
public class WebOracleService {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	@LoadBalanced
	protected RestTemplate restTemplate;

	//TODO for now it's hardcoded
	private final static String SERVICE_URL =  "http://oracle-service";

	public Optional<IGem> consult(String searchString) {
		logger.info("consult() invoked: for " + searchString);

		// needs to be a Gem.class
		IGem gem = restTemplate.getForObject(SERVICE_URL + "/gems?q={searchString}", Gem.class, searchString);
		return Optional.ofNullable(gem);
	}
	

	@Autowired
	private JmsTemplate jmsTemplate;

	public void save(List<String> wiseWords) {
		logger.info("save() invoked: for " + wiseWords.size());
	
		//TODO for now it's hardcoded
		wiseWords.forEach(wiseword -> {
			System.out.println("sending " + wiseword);
			jmsTemplate.send("wisewords", session -> session.createTextMessage(wiseword));	
		});
	}
	
	/**
	 * Until jigsaw allows us to hide a class from those who use this library this does the work...
	 */
	static class Gem implements IGem {

		private String essence;

		public Gem() {
		}

		public Gem(String essence) {
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
	        return format("Gem[essence='%s']", essence);
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
