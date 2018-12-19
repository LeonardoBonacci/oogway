package guru.bonacci.oogway.enricher;

import static java.util.UUID.randomUUID;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Serialized;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;

import guru.bonacci.oogway.domain.EnquiryEvent;

@SpringBootApplication
public class EnrichApp {

	public static void main(String[] args) {
		SpringApplication.run(EnrichApp.class, args);
	}

	interface Binding {

		String QUOTE = "quote";
		String ENRICH = "enrich";
		String BIGGEM = "biggem";

		@Input(QUOTE)
		KStream<?, ?> quote();

		@Input(ENRICH)
		KStream<?, ?> enrich();

		@Output(BIGGEM)
		KStream<?, ?> biggem();
	}

	@EnableBinding(Binding.class)
	public static class Processor {

		@SuppressWarnings("deprecation")
		@SendTo(Binding.BIGGEM)
		@StreamListener
		public KStream<String, BigGem> process(
				@Input(Binding.QUOTE) KStream<String, EnquiryEvent> quotes,
				@Input(Binding.ENRICH) KStream<String, Enrichment> enrich) {
			
			quotes.print("quote");

			KTable<String, Enrichment> enrichTable = enrich
					.groupByKey(Serialized.with(Serdes.String(), new EnrichmentSerde()))
					.reduce((aggValue, newValue) -> newValue);
			enrichTable.print("enrichment data");

			KStream<String, BigGem> join = quotes.leftJoin(enrichTable, 
					(q, r) -> new BigGem(q.getQuestion(), q.getApikey(), r),
					Joined.with(Serdes.String(), new EnquiryEventSerde(), new EnrichmentSerde()));

			KStream<String, BigGem> output = 
					join.map((k, v) -> new KeyValue<>(randomUUID().toString(), v));

			output.print("joined");
			return output;
		}
	}
}
