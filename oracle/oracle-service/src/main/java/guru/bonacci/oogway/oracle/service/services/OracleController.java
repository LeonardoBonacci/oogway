package guru.bonacci.oogway.oracle.service.services;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.oracle.service.beanmapping.GemMapper;
import guru.bonacci.oogway.oracle.service.persistence.Gem;
import guru.bonacci.oogway.oracle.service.persistence.GemRepository;
import guru.bonacci.oogway.shareddomain.GemCarrier;

@RestController
public class OracleController {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private GemRepository repo;

	@RequestMapping(path="/gems", method=GET)
	public Optional<GemCarrier> search(@RequestParam("q") String q, 
							 		   @RequestParam(value="by", required=false) Optional<String> author) {
		
		logger.info("Receiving request for a wise answer on: '" + q + "'");
		Optional<Gem> gem = author.map(a -> repo.consultTheOracle(q, a))
								  .orElse(repo.consultTheOracle(q));
		return gem.map(GemMapper.MAPPER::fromGem);
	}	

	@RequestMapping(path = "/gems/random", method=GET)
	public Optional<GemCarrier> random() {
		logger.info("Please find me a random gem");
		Optional<Gem> gem = repo.findRandom(); 
		return gem.map(GemMapper.MAPPER::fromGem);
	}	

	@RequestMapping(path = "/backdoor", method=POST)
	public void index(@RequestBody GemCarrier carrier) {
		logger.info("Receiving secret request to index: '" + carrier + "'");
		repo.saveTheNewOnly(GemMapper.MAPPER.toGem(carrier));
	}
	
	@RequestMapping(path = "/version", method=GET)
	public String version(@Value("${build.version}") String buildVersion) {
		return buildVersion;
	}	
}
