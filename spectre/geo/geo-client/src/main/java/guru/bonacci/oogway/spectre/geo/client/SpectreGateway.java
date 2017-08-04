package guru.bonacci.oogway.spectre.geo.client;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import guru.bonacci.oogway.spectre.geo.api._1984;

@MessagingGateway
public interface SpectreGateway {
	
	@Gateway(requestChannel = SpectreSource.CHANNEL_NAME)
	void generate(_1984 _1984);
}
