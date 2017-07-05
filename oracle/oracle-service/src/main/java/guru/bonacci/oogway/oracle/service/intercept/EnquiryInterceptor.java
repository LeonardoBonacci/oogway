package guru.bonacci.oogway.oracle.service.intercept;

import static org.slf4j.LoggerFactory.getLogger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannya.client.SannyaMessageProducer;

@Aspect
@Component
public class EnquiryInterceptor {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private SannyaMessageProducer messageProducer;

	@Before("@annotation(WatchMe) && args(searchString)")
	public void spreadTheNews(JoinPoint joinPoint, String searchString) {
		logger.info("Someone has asked you '" + searchString + "'");
		messageProducer.send(searchString);
	}
}