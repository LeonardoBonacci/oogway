package guru.bonacci.oogway.doorway.bigbrother;

import static org.slf4j.LoggerFactory.getLogger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.doorway.ip.IIPologist;
import guru.bonacci.oogway.doorway.utils.IPCatcher;

@Aspect
@Component
public class BigBrother {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	public IPCatcher iPCatcher;

	@Autowired
	private IIPologist ipologist;


	@Autowired
	private Lumbering lumbering;


    @Pointcut("@annotation(WatchMe)")
    public void watchMePointCut(){
    }

	@Before("watchMePointCut() && args(..,apiKey)")
	public void blockTheGreedyClients(JoinPoint joinPoint, String apiKey) throws Throwable {
		logger.debug("spotted: " + apiKey);

		lumbering.lumber(apiKey);
	}
}
