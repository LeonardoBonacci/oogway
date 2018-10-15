package guru.bonacci.spectre.localtimer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import guru.bonacci.spectre.localtimer.services.LocalTimerService;
import guru.bonacci.spectre.spectreshared.es.ElasticConfig;
import guru.bonacci.spectre.spectreshared.events.SpectreStreams;

@SpringBootApplication
@EnableBinding({SpectreStreams.class, Processor.class})
@Import(ElasticConfig.class)
public class LocalTimerServer {
	
	public static void main(String[] args) {
		SpringApplication.run(LocalTimerServer.class, args);
	}
	
	@Bean
	CommandLineRunner demo(LocalTimerService serv) {
		return args -> {
			serv.enrich("1be339b5-891c-43d6-ba30-22d97f916eda").subscribe(System.out::println);
		};
	}
}
