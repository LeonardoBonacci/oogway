package guru.bonacci.spectre.utilities.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface SpectreStreams {

	String ENRICHMENT = "enrichment";

    @Input(ENRICHMENT)
    SubscribableChannel enrichment();
}
