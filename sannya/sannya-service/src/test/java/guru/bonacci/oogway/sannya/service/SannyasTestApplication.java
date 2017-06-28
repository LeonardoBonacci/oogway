package guru.bonacci.oogway.sannya.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(SannyasTestConfig.class)
public class SannyasTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SannyasTestApplication.class, args);
	}
}