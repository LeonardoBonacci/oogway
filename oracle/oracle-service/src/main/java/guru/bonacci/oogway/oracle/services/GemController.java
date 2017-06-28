package guru.bonacci.oogway.oracle.services;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.oracle.api.IGem;
import guru.bonacci.oogway.oracle.persistence.Gem;
import guru.bonacci.oogway.oracle.persistence.GemRepository;

@RestController
public class GemController {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private GemRepository repo;

	@RequestMapping(path = "/gems", method = GET)
	public IGem search(@RequestParam("q") String q) {
		logger.info("Receiving request for a wise answer on: '" + q + "'");
		Optional<Gem> gem = repo.consultTheOracle(q); 
		return gem.isPresent() ? (IGem) gem.get() : null;
	}	

	@RequestMapping(path = "/backdoor", method = POST)
	public void index(@RequestBody String input) {
		logger.info("receiving backdoor index request for: '" + input + "'");
		repo.saveTheNewOnly(new Gem(input));
	}
}
