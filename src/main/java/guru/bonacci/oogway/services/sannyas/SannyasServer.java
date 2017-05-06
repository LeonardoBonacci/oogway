package guru.bonacci.oogway.services.sannyas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.es.ESConfig;
import guru.bonacci.oogway.jms.JMSConfig;
import guru.bonacci.oogway.sannyas.AManager;
import guru.bonacci.oogway.sannyas.SannyasConfiguration;

@EnableAutoConfiguration
@EnableDiscoveryClient
@Import({JMSConfig.class, SannyasConfiguration.class, ESConfig.class})
public class SannyasServer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "sannyas-server");
		SpringApplication.run(SannyasServer.class, args);
	}
	
	@RestController
	class AController {

		private final AManager manager;

		@Autowired
		public AController(AManager manager) {
			this.manager = manager;
		}

		@RequestMapping(path = "/backdoor", method = RequestMethod.POST)
		public void index(@RequestBody String input) {
			manager.delegate(input);
		}
	}
}
