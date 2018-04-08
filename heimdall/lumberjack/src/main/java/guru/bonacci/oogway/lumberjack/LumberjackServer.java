package guru.bonacci.oogway.lumberjack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class LumberjackServer {
	
	public static void main(String[] args) {
		SpringApplication.run(LumberjackServer.class, args);
	}
}
