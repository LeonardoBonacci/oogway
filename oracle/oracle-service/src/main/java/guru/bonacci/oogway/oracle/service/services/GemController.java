package guru.bonacci.oogway.oracle.service.services;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.oracle.service.beanmapping.GemMapper;
import guru.bonacci.oogway.oracle.service.persistence.Gem;
import guru.bonacci.oogway.oracle.service.persistence.GemRepository;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/gems")
@Api(value = "gemming", description = "Made for Gem Mining")
public class GemController {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private GemRepository repo;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new GemValidator());
	}

	@ApiOperation(value = "Search for a gem", response = GemCarrier.class)
	@RequestMapping(method = GET)
	public Optional<GemCarrier> search(@RequestParam("q") String q, 
							 		   @RequestParam(value="by", required=false) Optional<String> author) {
		logger.info("Receiving request for a wise answer on: '" + q + "'");
		
		Optional<Gem> gem = author.map(a -> repo.consultTheOracle(q, a))
								  .orElse(repo.consultTheOracle(q));
		return gem.map(GemMapper.MAPPER::fromGem);
	}	

	@ApiOperation(value = "Pick a random gem", response = GemCarrier.class)
	@RequestMapping(path = "/random", method = GET)
	public Optional<GemCarrier> random() {
		logger.info("Please find me a random gem");
		
		Optional<Gem> gem = repo.findRandom(); 
		return gem.map(GemMapper.MAPPER::fromGem);
	}	

	@ApiOperation(value = "Add a gem")
	@RequestMapping(path = "/backdoor", method = POST)
	public void index(@Valid @RequestBody GemCarrier carrier) {
		logger.info("Receiving secret request to index: '" + carrier + "'");
		
		repo.saveTheNewOnly(GemMapper.MAPPER.toGem(carrier));
	}
	
	@ApiOperation(value = "What's the version again?")
	@RequestMapping(path = "/version", method = GET)
	public String version(@Value("${build.version}") String buildVersion) {
		return buildVersion;
	}	
}
