package guru.bonacci.oogway.web.intercept;

import static org.slf4j.LoggerFactory.getLogger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.spectre.geo.client.GeoMessageProducer;
import guru.bonacci.oogway.web.utils.Catch22;

@Aspect
@Component
public class BigBrother {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	public Catch22 catch22;

	@Autowired
	private GeoMessageProducer messageProducer;

	@Before("@annotation(WatchMe) && args(searchString)")
	public void spreadTheNews(JoinPoint joinPoint, String searchString) {
		String ip = catch22.getClientIp();
		logger.info(ip + " said '" + searchString + "'");
		messageProducer.send(ip, searchString);
	}
}
