package guru.bonacci.oogway.doorway.events;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

import guru.bonacci.oogway.domain.EnquiryEvent;

public interface Binding {

	String OUTPUT = "output";
	String INPUT = "input";

	@Output(OUTPUT)
	MessageChannel output();
	
	@Input(INPUT)
	KStream<Object, EnquiryEvent> input();
}
