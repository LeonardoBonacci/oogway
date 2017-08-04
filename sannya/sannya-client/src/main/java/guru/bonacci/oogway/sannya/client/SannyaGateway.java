package guru.bonacci.oogway.sannya.client;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface SannyaGateway {
	
	@Gateway(requestChannel = SannyaSource.CHANNEL_NAME)
	void generate(Wrapper wrapper);
}
