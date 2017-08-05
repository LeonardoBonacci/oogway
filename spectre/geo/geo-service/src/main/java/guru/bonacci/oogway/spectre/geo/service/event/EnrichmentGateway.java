package guru.bonacci.oogway.spectre.geo.service.event;

import static guru.bonacci.oogway.spectre.geo.service.event.GeoEventChannels.ENRICHMENT;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import guru.bonacci.oogway.secretdomain.GenericEvent;

@MessagingGateway
public interface EnrichmentGateway {
	
	@Gateway(requestChannel = ENRICHMENT)
	void send(GenericEvent event);
}
