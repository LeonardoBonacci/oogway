package guru.bonacci.oogway.spectre.geo.service.services;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface SpectreProcessor {

	String CHANNEL_NAME = "worksChannel";

    @Input
    SubscribableChannel worksChannel();
    
    
	String ENRICHMENT = "enrichment";

	@Output
	MessageChannel enrichment();
}
