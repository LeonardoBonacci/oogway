package guru.bonacci.oogway.lumberjack.services;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.lumberjack.persistence.Log;
import guru.bonacci.oogway.lumberjack.persistence.LogService;

@RestController
public class LogController {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private LogService service;
	
	@RequestMapping(path = "/visits/{apikey}", method = GET)
	public Long log(@PathVariable("apikey") String apiKey) {
		logger.info("Visit key: " + apiKey);

		return service.insert(new Log(apiKey));
	}
}
