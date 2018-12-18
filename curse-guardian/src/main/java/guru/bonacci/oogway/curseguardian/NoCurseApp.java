package guru.bonacci.oogway.curseguardian;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsProcessor;
import org.springframework.messaging.handler.annotation.SendTo;

import guru.bonacci.oogway.domain.GemCarrier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
		public KStream<String, Some> process(KStream<String, Some> gems) {
			gems.print("aaa");
//			gems.mapValues(v -> v.getAuthor()).print("r");

//		    return gems.filter((k,v) -> profane.test(v.getSaying()))
//		    		  .map((k,v) -> new KeyValue<>(v.getAuthor(), v));
			return gems;
		}
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public class Some {

		private String author, comment;
	}

}
