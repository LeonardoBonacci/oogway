package guru.bonacci.oogway.enricher;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;

import guru.bonacci.oogway.domain.EnquiryEvent;
import guru.bonacci.oogway.domain.GemCarrier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@SpringBootApplication
public class EnrichApp {
	
	public static void main(String[] args) {
		SpringApplication.run(EnrichApp.class, args);
	}
	
	interface Binding {

		String ENRICH = "enrich";

		@Input(ENRICH)
		KStream<?, ?> enrich();
	}

//	@RequiredArgsConstructor
//	@EnableBinding(Binding.class)
//	public static class Processor {
//
////		@SendTo("output")
//		@StreamListener
//		public void process(@Input(Binding.QUOTES) KStream<?, GemCarrier> gems, 
//		                    @Input(Binding.ENRICH) KStream<String, Rich> riches) {
//			riches.print("g");
////			riches.mapValues(v -> v.getAuthor()).print("r");
////			KStream<?, BigGem> leftJoin = 
////	        		riches.leftJoin(gems, (r, g) ->  new BigGem(g, r), JoinWindows.of(5000));
////			leftJoin.print("j");
////			leftJoin.foreach((k,v) -> send(v));
////			return riches;
//		}
	@RequiredArgsConstructor
	@EnableBinding(Binding.class)
	public static class Processor {

//		@SendTo("output")
		@StreamListener
		public void process(@Input(Binding.ENRICH) KStream<String, EnquiryEvent> riches) {
			riches.print("g");
//			riches.mapValues(v -> v.getAuthor()).print("r");
//			KStream<?, BigGem> leftJoin = 
//	        		riches.leftJoin(gems, (r, g) ->  new BigGem(g, r), JoinWindows.of(5000));
//			leftJoin.print("j");
//			leftJoin.foreach((k,v) -> send(v));
//			return riches;
		}

		@Data
		@NoArgsConstructor
		@AllArgsConstructor
		public class Rich {

			private String author, comment;
		}

		@Data
		@NoArgsConstructor
		@AllArgsConstructor
		public class BigGem {

			private GemCarrier gem;
			private Rich rich;
		}

	}
	
}
