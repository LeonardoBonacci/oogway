package guru.bonacci.oogway.enricher;

import org.springframework.kafka.support.serializer.JsonSerde;

import guru.bonacci.oogway.domain.EnquiryEvent;

public class EnquiryEventSerde extends JsonSerde<EnquiryEvent> {}