package guru.bonacci.oogway.spectre.geo.client;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.spectre.geo.api._1984;

/**
 * Talks to Geo via messaging
 */
@Component
public class Nineteen84MessageProducer {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private JmsTemplate jmsTemplate;

	@Value("${spring.activemq.queue.to-geo}")
	private String queue;

	public void send(String ip, String message) {
		logger.info(queue);
		jmsTemplate.send(queue, session -> session.createObjectMessage(new _1984(ip, message)));
//		jmsTemplate.send(queue, session -> session.createTextMessage("from web"));

	}
}
