package guru.bonacci.oogway.sannya.client;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SannyaMessageProducer {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private JmsTemplate jmsTemplate;

	@Value("${spring.activemq.queue.to-sannya}")
	private String queue;

	public void send(String searchString) {
		logger.info("Spread the news: '" + searchString + "'");
		jmsTemplate.send(queue, session -> session.createTextMessage(searchString));
	}
}
