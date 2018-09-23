package guru.bonacci.oogway.doorway.bigbrother;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.doorway.exceptions.GreedyException;

@Component
@ConditionalOnMissingBean(ToLumber.class)
public class OrNotToLumber implements Lumbering {

	@Override
	public void lumber(String apikey) throws GreedyException {
		//doing nothing
	}
}
