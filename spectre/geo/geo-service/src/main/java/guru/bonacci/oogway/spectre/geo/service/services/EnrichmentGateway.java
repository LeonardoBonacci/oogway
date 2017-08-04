package guru.bonacci.oogway.spectre.geo.service.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface EnrichmentGateway {
	
	@Gateway(requestChannel = SpectreProcessor.ENRICHMENT)
	void enrich(Wrapper uuid);
}
