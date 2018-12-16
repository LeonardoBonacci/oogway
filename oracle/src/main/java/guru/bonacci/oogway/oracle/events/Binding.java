package guru.bonacci.oogway.oracle.events;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Binding {

	String OUTPUT = "output1";

	@Output(OUTPUT)
	MessageChannel output();
}
