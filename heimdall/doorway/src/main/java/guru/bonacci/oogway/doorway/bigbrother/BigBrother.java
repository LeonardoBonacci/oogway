package guru.bonacci.oogway.doorway.bigbrother;

import static org.slf4j.LoggerFactory.getLogger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.doorway.clients.LumberjackClient;
import guru.bonacci.oogway.doorway.exceptions.GreedyException;
import guru.bonacci.oogway.doorway.ip.IIPologist;
import guru.bonacci.oogway.doorway.utils.IPCatcher;

@Aspect
@Component
public class BigBrother {

	private final Logger logger = getLogger(this.getClass());

	private static final Long GREED_STARTS_HERE = 10l;
	
	@Autowired
	private LumberjackClient lumberClient;

	@Autowired
	public IPCatcher iPCatcher;

	@Autowired
	private IIPologist ipologist;

    @Pointcut("@annotation(WatchMe)")
    public void watchMePointCut(){
    }

	@Before("watchMePointCut() && args(..,apiKey)")
	public void blockTheGreedyClients(JoinPoint joinPoint, String apiKey) throws Throwable {
//		long visits = lumberClient.visits(apiKey);
//		if (!"yawgoo".equals(apiKey) && visits >= GREED_STARTS_HERE) { //this could be user specific info
//			throw new GreedyException();
//		}
	}
}
