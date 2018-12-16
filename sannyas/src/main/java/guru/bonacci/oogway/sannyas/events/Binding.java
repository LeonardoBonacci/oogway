package guru.bonacci.oogway.sannyas.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface Binding {

	String INPUT = "input";
	String OUTPUT = "output";

	@Input(INPUT)
	SubscribableChannel input();

	@Output(OUTPUT)
	MessageChannel output();
}
