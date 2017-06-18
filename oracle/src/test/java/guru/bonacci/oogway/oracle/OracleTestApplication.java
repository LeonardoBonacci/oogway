package guru.bonacci.oogway.oracle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(OracleTestConfig.class)
public class OracleTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(OracleTestApplication.class, args);
	}
}