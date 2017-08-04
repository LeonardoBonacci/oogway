package guru.bonacci.oogway.oracle.client;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface OracleGateway {
	
	@Gateway(requestChannel = OracleSource.CHANNEL_NAME)
	void generate(Wrapper wrapper);
}
