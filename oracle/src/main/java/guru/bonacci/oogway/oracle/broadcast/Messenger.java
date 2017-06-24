package guru.bonacci.oogway.oracle.broadcast;

import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Messenger {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private JmsTemplate jmsTemplate;

	@Value("${spring.activemq.queue.name}")
	private String queue;


	@Before("@annotation(WatchMe) && args(searchString)")
	public void spreadTheNews(JoinPoint joinPoint, String searchString) {
		logger.info(format("Spread the news: '%s'", searchString));
		jmsTemplate.send(queue, session -> session.createTextMessage(searchString));
	}
}
