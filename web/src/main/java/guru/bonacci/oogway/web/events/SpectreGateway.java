package guru.bonacci.oogway.web.events;

import static guru.bonacci.oogway.web.events.WebEventChannels.SPECTRE;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import guru.bonacci.oogway.spectre.geo.api._1984;

@MessagingGateway
public interface SpectreGateway {
	
	@Gateway(requestChannel = SPECTRE)
	void send(_1984 _1984);
}
