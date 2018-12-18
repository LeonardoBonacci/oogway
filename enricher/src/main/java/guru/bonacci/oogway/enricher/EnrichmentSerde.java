package guru.bonacci.oogway.enricher;

import org.springframework.kafka.support.serializer.JsonSerde;

public class EnrichmentSerde extends JsonSerde<Enrichment> {}