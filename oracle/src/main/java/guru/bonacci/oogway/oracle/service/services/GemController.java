package guru.bonacci.oogway.oracle.service.services;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.oracle.service.beanmapping.GemMapper;
import guru.bonacci.oogway.oracle.service.persistence.Gem;
import guru.bonacci.oogway.shareddomain.GemCarrier;

@RestController
@RequestMapping("/gems")
public class GemController {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private GemService service;

	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new GemValidator());
	}

	@GetMapping
	public Optional<GemCarrier> search(@RequestParam("q") String q, 
							 		   @RequestParam(value="by", required = false) Optional<String> author) {
		logger.info("Receiving request for a wise answer on: '" + q + "'");
		
		Optional<Gem> gem = service.search(q, author);
		return gem.map(GemMapper.MAPPER::fromGem);
	}	

	@GetMapping("/random")
	public Optional<GemCarrier> random() {
		logger.info("Please find me a random gem");
		
		Optional<Gem> gem = service.random(); 
		gem.ifPresent(g -> logger.info("Random gem found: " + g.getSaying()));
		return gem.map(GemMapper.MAPPER::fromGem);
	}	

	@PostMapping("/backdoor")
	public void index(@Valid @RequestBody GemCarrier carrier) {
		logger.info("Receiving secret request to index: '" + carrier + "'");
		
		service.index(GemMapper.MAPPER.toGem(carrier));
	}
}
