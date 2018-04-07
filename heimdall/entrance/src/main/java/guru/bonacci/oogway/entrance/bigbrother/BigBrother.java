package guru.bonacci.oogway.entrance.bigbrother;

import static org.slf4j.LoggerFactory.getLogger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.entrance.events.SpectreGateway;
import guru.bonacci.oogway.entrance.ip.IIPologist;
import guru.bonacci.oogway.entrance.utils.IPCatcher;
import guru.bonacci.oogway.shareddomain.COMINT;

@Aspect
@Component
public class BigBrother {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	public IPCatcher iPCatcher;

	@Autowired
	private IIPologist ipologist;

	@Autowired
    private SpectreGateway gateway;

	@Before("@annotation(WatchMe) && args(searchString,..)")
	public void spreadTheNews(JoinPoint joinPoint, String searchString) {
		String ip = ipologist.checkUp(iPCatcher.getClientIp());
		
		logger.info(ip + " said '" + searchString + "'");
		gateway.send(new COMINT(ip, searchString));
	}
}
