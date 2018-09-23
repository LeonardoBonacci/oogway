package guru.bonacci.oogway.doorway.lumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.doorway.clients.LumberjackClient;
import guru.bonacci.oogway.doorway.exceptions.GreedyException;

@Component
@ConditionalOnProperty(name = "service.lumberjack.enabled", havingValue = "true")
public class ToLumber implements Lumberjack {

	private static final Long GREED_STARTS_HERE = 6l;
	
	@Autowired
	private LumberjackClient lumberClient;

	@Override
	public void lumber(String apiKey) throws GreedyException {
		long visits = lumberClient.visits(apiKey);
		if (!"yawgoo".equals(apiKey) && visits >= GREED_STARTS_HERE) { //this could be user specific info
			throw new GreedyException();
		}
	}
}
