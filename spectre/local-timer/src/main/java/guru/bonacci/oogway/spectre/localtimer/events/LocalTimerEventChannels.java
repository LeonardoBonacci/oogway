package guru.bonacci.oogway.spectre.localtimer.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface LocalTimerEventChannels {

	String ENRICHMENT = "enrichment";

    @Input(ENRICHMENT)
    SubscribableChannel enrichmentChannel();
}
