package guru.bonacci.spectre.localtimer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;

import guru.bonacci.spectre.spectreshared.events.SpectreStreams;

@SpringBootApplication
@EnableBinding({SpectreStreams.class, Processor.class})
public class LocalTimerServer {
	
	public static void main(String[] args) {
		SpringApplication.run(LocalTimerServer.class, args);
	}
}
