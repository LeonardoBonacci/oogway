package guru.bonacci.oogway.services.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.es.ESConfig;
import guru.bonacci.oogway.jms.JMSConfig;
import guru.bonacci.oogway.sannyas.SannyasConfiguration;

@SpringBootApplication
@EnableDiscoveryClient
@Import({ JMSConfig.class, ESConfig.class, SannyasConfiguration.class })
public class WebServer {

	public static void main(String[] args) {
		System.setProperty("spring.config.name", "web-server");
		SpringApplication.run(WebServer.class, args);
	}

	@RestController
	class AController {

		private final AService service;

		@Autowired
		public AController(AService service) {
			this.service = service;
		}

		@RequestMapping(path = "/", method = RequestMethod.GET)
		public String index(@RequestParam("q") String q) {
			return service.index(q);
		}
	}
}
