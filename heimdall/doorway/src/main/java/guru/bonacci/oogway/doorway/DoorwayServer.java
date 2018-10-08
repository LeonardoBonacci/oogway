package guru.bonacci.oogway.doorway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.IntegrationComponentScan;

import guru.bonacci.oogway.doorway.events.DoorwayEventChannels;

@SpringBootApplication
@EnableBinding(DoorwayEventChannels.class)
@IntegrationComponentScan
public class DoorwayServer {

	public static void main(String[] args) {
		SpringApplication.run(DoorwayServer.class, args);
	}
}
