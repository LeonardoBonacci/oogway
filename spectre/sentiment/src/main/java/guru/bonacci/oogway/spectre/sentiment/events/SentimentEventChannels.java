package guru.bonacci.oogway.spectre.sentiment.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface SentimentEventChannels {

	String ENRICHMENT = "enrichment";

    @Input(ENRICHMENT)
    SubscribableChannel enrichmentChannel();
}
