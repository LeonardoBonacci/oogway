package guru.bonacci.oogway.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WebTestConfig.class)
public class WebTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebTestApplication.class, args);
	}
}