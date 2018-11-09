package guru.bonacci.oogway.jobs.tutor;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class Runner {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
        				.filter(basicAuthentication("jobs","sboj"))
        				.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
    
    @Bean
	CommandLineRunner demo(Educator ed) {
		return args -> {
			ed.ucate();
		};
    }
}