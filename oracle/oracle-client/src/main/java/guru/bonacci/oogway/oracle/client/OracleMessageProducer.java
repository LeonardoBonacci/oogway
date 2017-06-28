package guru.bonacci.oogway.oracle.client;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class OracleMessageProducer {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private JmsTemplate jmsTemplate;

	@Value("${spring.activemq.queue.to-oracle:to-oracle}")
	private String queue;

	public void save(List<String> wiseWords) {
		wiseWords.forEach(wiseword -> {
			logger.info("sending: " + wiseword);
			jmsTemplate.send(queue, session -> session.createTextMessage(wiseword));
		});
	}
}
