package guru.bonacci.oogway.oracle.api;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

		IGem gem = restTemplate.getForObject(SERVICE_URL + "/gems?q={searchString}", IGem.class, searchString);
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
}
