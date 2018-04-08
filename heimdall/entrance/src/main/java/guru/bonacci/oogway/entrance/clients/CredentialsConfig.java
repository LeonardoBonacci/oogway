package guru.bonacci.oogway.entrance.clients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import feign.codec.Decoder;

@Configuration
@Profile("!unit-test") // hack :)
public class CredentialsConfig {
	
	@Bean
	Decoder decoder() {
		return new CredentialsDecoder();
	}
}