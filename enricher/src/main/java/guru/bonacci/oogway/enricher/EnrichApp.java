package guru.bonacci.oogway.enricher;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;

import guru.bonacci.oogway.domain.EnquiryEvent;


@SpringBootApplication
public class EnrichApp {
	
	public static void main(String[] args) {
		SpringApplication.run(EnrichApp.class, args);
	}
	
	interface Binding {

		String ENRICH = "enrich";
		String QUOTE = "quote";

		@Input(ENRICH)
		KStream<?, ?> enrich();

		@Input(QUOTE)
		KStream<?, ?> quote();
	}


	@EnableBinding(Binding.class)
	public static class Processor {

		@StreamListener
		public void process(@Input(Binding.QUOTE) KStream<String, EnquiryEvent> quotes,
							@Input(Binding.ENRICH) KStream<String, Enrichment> riches) {
			quotes.print("q");
			riches.print("r");
			KStream<?, BigGem> leftJoin = 
					quotes.leftJoin(riches, 
							(q, r) ->  new BigGem(q, r), 
							JoinWindows.of(5000),
							Joined.with(Serdes.String(), 
									new EnquiryEventSerde(),
									new EnrichmentSerde()));
			leftJoin.print("joint");
			System.out.println("stop");
			
		}
	}
	
}
