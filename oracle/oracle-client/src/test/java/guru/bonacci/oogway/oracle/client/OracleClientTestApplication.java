package guru.bonacci.oogway.oracle.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(OracleClientTestConfig.class)
public class OracleClientTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(OracleClientTestApplication.class, args);
	}
}