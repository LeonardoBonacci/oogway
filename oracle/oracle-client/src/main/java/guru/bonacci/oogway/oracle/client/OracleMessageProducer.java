package guru.bonacci.oogway.oracle.client;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Talks to the Oracle via messaging
 */
@Component
public class OracleMessageProducer {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private JmsTemplate jmsTemplate;

	@Value("${spring.activemq.queue.to-oracle}")
	private String queue;

	public void save(List<String> wiseSayings) {
		wiseSayings.forEach(wisewords -> {
			logger.info("Sending to the Oracle '" + wisewords + "'");
			jmsTemplate.send(queue, session -> session.createTextMessage(wisewords));
		});
	}
}
