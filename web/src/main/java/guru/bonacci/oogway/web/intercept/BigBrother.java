package guru.bonacci.oogway.web.intercept;

import static org.slf4j.LoggerFactory.getLogger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.spectre.geo.api._1984;
import guru.bonacci.oogway.web.events.SpectreGateway;
import guru.bonacci.oogway.web.utils.IPCatcher;

@Aspect
@Component
public class BigBrother {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	public IPCatcher iPCatcher;

	@Autowired
    private SpectreGateway gateway;

	@Before("@annotation(WatchMe) && args(searchString)")
	public void spreadTheNews(JoinPoint joinPoint, String searchString) {
		String ip = iPCatcher.getClientIp();
		logger.info(ip + " said '" + searchString + "'");
		gateway.send(new _1984(ip, searchString));
	}
}
