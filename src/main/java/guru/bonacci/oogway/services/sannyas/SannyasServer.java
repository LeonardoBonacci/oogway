package guru.bonacci.oogway.services.sannyas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.es.ESConfig;
import guru.bonacci.oogway.jms.JMSConfig;
import guru.bonacci.oogway.sannyas.MyManager;
import guru.bonacci.oogway.sannyas.SannyasConfiguration;

/**
 * Micro-service for the retrieval and indexing of wisdom
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableCaching
@Import({JMSConfig.class, SannyasConfiguration.class, ESConfig.class})
public class SannyasServer {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	 
	public static void main(String[] args) {
		System.setProperty("spring.config.name", "sannyas-server");
		SpringApplication.run(SannyasServer.class, args);
	}

	@RestController
	class AController {

		@Autowired
		private MyManager manager;

		@RequestMapping(path = "/backdoor", method = RequestMethod.POST)
		public void index(@RequestBody String input) {
			logger.info("receiving backdoor index request for: '" + input + "'");
			manager.delegate(input);
		}
	}
}
