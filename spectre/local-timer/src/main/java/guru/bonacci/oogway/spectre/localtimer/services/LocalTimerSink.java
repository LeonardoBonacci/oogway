package guru.bonacci.oogway.spectre.localtimer.services;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface LocalTimerSink {

	String ENRICHMENT = "enrichment";

    @Input
    SubscribableChannel enrichment();
}
