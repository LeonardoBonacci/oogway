package guru.bonacci.oogway.lumberjack.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.lumberjack.persistence.Log;
import guru.bonacci.oogway.lumberjack.persistence.LogService;
import reactor.core.publisher.Mono;

@RestController
public class LogController {

	@Autowired
	private LogService service;
	
	@GetMapping("/visits/{apikey}")
	public Mono<Long> log(@PathVariable("apikey") String apiKey) {
		return service.insert(new Log(apiKey));
	}
}
