package guru.bonacci.oogway.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServer {

	public static void main(String[] args) {
		if (args.length == 1) {
			// Optionally set the HTTP port to listen on, overrides
			// value in properties file
			System.setProperty("server.port", args[0]);
		}
		SpringApplication.run(ConfigServer.class, args);
	}
}