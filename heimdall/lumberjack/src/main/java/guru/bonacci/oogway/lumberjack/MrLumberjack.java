package guru.bonacci.oogway.lumberjack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@SpringBootApplication
public class MrLumberjack {
	
	public static void main(String[] args) {
		SpringApplication.run(MrLumberjack.class, args);
	}
}
