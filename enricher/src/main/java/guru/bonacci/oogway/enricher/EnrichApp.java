package guru.bonacci.oogway.enricher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

import guru.bonacci.oogway.enricher.events.Binding;


@EnableBinding(Binding.class)
@SpringBootApplication
public class EnrichApp {

	public static void main(String[] args) {
		SpringApplication.run(EnrichApp.class, args);
	}
}
