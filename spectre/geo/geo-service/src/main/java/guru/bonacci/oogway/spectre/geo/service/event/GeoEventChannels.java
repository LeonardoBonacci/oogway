package guru.bonacci.oogway.spectre.geo.service.event;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface GeoEventChannels {

	String SPECTRE = "spectre";

	@Input(SPECTRE)
    SubscribableChannel spectreChannel();
    
    
	String ENRICHMENT = "enrichment";

	@Output(ENRICHMENT)
	MessageChannel enrichmentChannel();
}
