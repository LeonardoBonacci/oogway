package guru.bonacci.oogway.enricher;

import static java.util.UUID.randomUUID;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Serialized;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;

import guru.bonacci.oogway.domain.GemCarrier;
import guru.bonacci.oogway.domain.GemCarrierSerde;
import guru.bonacci.oogway.enricher.domain.BigGem;
import guru.bonacci.oogway.enricher.domain.Enrichment;
import guru.bonacci.oogway.enricher.domain.EnrichmentSerde;

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

		@SendTo(Binding.BIGGEM)
		@StreamListener
		public KStream<String, BigGem> process(
				@Input(Binding.QUOTE) KStream<String, GemCarrier> gems,
				@Input(Binding.ENRICH) KStream<String, Enrichment> enrich) {
			
			gems.print(Printed.<String, GemCarrier>toSysOut().withLabel("gem"));

			KTable<String, Enrichment> enrichTable = enrich
					.groupByKey(Serialized.with(Serdes.String(), new EnrichmentSerde()))
					.reduce((aggValue, newValue) -> newValue);

			enrichTable.toStream().print(Printed.<String, Enrichment>toSysOut().withLabel("enrich"));

			KStream<String, BigGem> join = gems.leftJoin(enrichTable, 
					(q, r) -> new BigGem(q.getSaying(), q.getAuthor(), r),
					Joined.with(Serdes.String(), new GemCarrierSerde(), new EnrichmentSerde()));

			KStream<String, BigGem> output = 
					join.map((k, v) -> new KeyValue<>(randomUUID().toString(), v));

			output.print(Printed.<String, BigGem>toSysOut().withLabel("output"));
			return output;
		}
	}
}
