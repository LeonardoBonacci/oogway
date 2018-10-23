package guru.bonacci.oogway.doorway.clients;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

import java.util.function.BiFunction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	
	@Bean
    public BiFunction<String, String, WebClient> webClientFactory() {
        return (user, password) -> webClient(user, password);
    } 
	
	@Scope(value = SCOPE_PROTOTYPE)
	@Bean
	public WebClient webClient(String user, String password) {
        return WebClient.builder()
        				.filter(basicAuthentication(user, password))
        				.build();
    }
}

