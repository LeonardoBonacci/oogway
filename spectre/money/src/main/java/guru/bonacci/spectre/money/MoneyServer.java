package guru.bonacci.spectre.money;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

import guru.bonacci.spectre.spectreshared.events.SpectreStreams;

@SpringBootApplication
@EnableBinding(SpectreStreams.class)
public class MoneyServer {
	
	public static void main(String[] args) {
		SpringApplication.run(MoneyServer.class, args);
	}
}
