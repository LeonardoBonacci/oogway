package guru.bonacci.oogway.spectre.geo.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(GeoTestConfig.class)
public class GeoTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeoTestApplication.class, args);
	}
}