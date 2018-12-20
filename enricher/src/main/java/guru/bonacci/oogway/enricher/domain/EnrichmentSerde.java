package guru.bonacci.oogway.enricher.domain;

import org.springframework.kafka.support.serializer.JsonSerde;

public class EnrichmentSerde extends JsonSerde<Enrichment> {}