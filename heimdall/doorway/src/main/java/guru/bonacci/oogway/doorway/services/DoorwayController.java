package guru.bonacci.oogway.doorway.services;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.shareddomain.GemCarrier;
import reactor.core.publisher.Mono;

@RestController
public class DoorwayController {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private FirstLineSupportService serv;

	@GetMapping
	public String test() {
		logger.warn("found");
		return "Alive!";
	}

	@GetMapping("/iam/{apikey}")
	public Mono<GemCarrier> enquire(@PathVariable("apikey") String apikey, @RequestParam("q") String q) {
		logger.info("Receiving request for a wise answer on: '" + q + "'");

		return serv.enquire(q, apikey);
	}
}
