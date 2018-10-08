package guru.bonacci.oogway.doorway.lumber;

import org.springframework.stereotype.Component;

import guru.bonacci.oogway.doorway.exceptions.GreedyException;

@Component
public class ToLumber implements Lumberjack {
	
	@Override
	public void lumber(String apiKey) throws GreedyException {
	}
}
