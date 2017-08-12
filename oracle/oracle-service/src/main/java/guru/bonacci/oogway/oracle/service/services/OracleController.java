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

import guru.bonacci.oogway.oracle.service.persistence.Gem;
import guru.bonacci.oogway.oracle.service.persistence.GemRepository;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import guru.bonacci.oogway.shareddomain.IGem;

@RestController
public class OracleController {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private GemRepository repo;

	@RequestMapping(path = "/gems", method = GET)
	public IGem search(	@RequestParam("q") String q, 
						@RequestParam(value = "by", required = false) Optional<String> author) {
		
		logger.info("Receiving request for a wise answer on: '" + q + "'");
		Optional<Gem> gem = repo.consultTheOracle(q, author); 
		return (IGem)gem.orElse(null);
	}	

	@RequestMapping(path = "/backdoor", method = POST)
	public void index(@RequestBody GemCarrier gem) {
		logger.info("Receiving secret request to index: '" + gem + "'");
		repo.saveTheNewOnly(new Gem(gem.getSaying(), gem.getAuthor()));
	}
	
	@RequestMapping(path = "/version", method = GET)
	public String version(@Value("${build.version}") String buildVersion) {
		return buildVersion;
	}	
}
