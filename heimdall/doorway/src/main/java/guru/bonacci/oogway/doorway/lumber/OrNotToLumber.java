package guru.bonacci.oogway.doorway.lumber;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.doorway.exceptions.GreedyException;

@Component
@ConditionalOnMissingBean(ToLumber.class)
public class OrNotToLumber implements Lumberjack {

	@Override
	public void lumber(String apikey) throws GreedyException {
		//doing nothing
	}
}
