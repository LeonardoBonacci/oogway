package guru.bonacci.oogway.doorway.lumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.doorway.clients.LumberClient;
import guru.bonacci.oogway.doorway.exceptions.GreedyException;

@Component
@ConditionalOnProperty(name = "service.lumberjack.enabled", havingValue = "true")
public class ToLumber implements Lumberjack {

	private static final Long GREED_STARTS_HERE = 10l;
	
	@Autowired
	private LumberClient lumberClient;

	@Override
	public void lumber(String apikey) throws GreedyException {
		// Called from a webfilter: cannot be included in reactive chain
		if (lumberClient.visits(apikey).block() >= GREED_STARTS_HERE)
			throw new GreedyException();
	}
}