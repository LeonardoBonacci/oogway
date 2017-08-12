package guru.bonacci.oogway.sannya.service.events;

import static guru.bonacci.oogway.sannya.service.events.SannyaEventChannels.SANNYA;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import guru.bonacci.oogway.shareddomain.GemCarrier;

@MessagingGateway
public interface SannyaGateway {
	
	@Gateway(requestChannel = SANNYA)
	void send(GemCarrier event);
}
