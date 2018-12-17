package guru.bonacci.oogway.curseguardian;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsProcessor;
import org.springframework.messaging.handler.annotation.SendTo;

import guru.bonacci.oogway.domain.GemCarrier;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
public class NoCurseApp {
	
	public static void main(String[] args) {
		SpringApplication.run(NoCurseApp.class, args);
	}
	
	@RequiredArgsConstructor
	@EnableBinding(KafkaStreamsProcessor.class)
	public static class Processor {

		private final ProfanityFilter profane;

		@SendTo("output")
		@StreamListener("input")
		public KStream<?, GemCarrier> process(KStream<?, GemCarrier> line) {
		    return line.filter((k,v) -> profane.test(v.getSaying()));
		}
	}

}
