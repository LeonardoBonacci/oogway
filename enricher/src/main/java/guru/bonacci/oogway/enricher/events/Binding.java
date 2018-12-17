package guru.bonacci.oogway.enricher.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Binding {

	String INPUT = "input";

	@Input(INPUT)
	SubscribableChannel input();
}
