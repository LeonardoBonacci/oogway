package guru.bonacci.oogway.sannya.client;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SannyaMessageProducer {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
    private SannyaGateway gateway;

	public void send(String searchString) {
		logger.info("Spread the news: '" + searchString + "'");
		gateway.generate(new Wrapper(searchString));
	}
}
