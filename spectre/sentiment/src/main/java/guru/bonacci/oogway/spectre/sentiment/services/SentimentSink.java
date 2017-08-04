package guru.bonacci.oogway.spectre.sentiment.services;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface SentimentSink {

	String ENRICHMENT = "enrichment";

    @Input
    SubscribableChannel enrichment();
}
