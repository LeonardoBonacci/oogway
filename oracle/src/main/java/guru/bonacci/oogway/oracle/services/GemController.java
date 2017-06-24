package guru.bonacci.oogway.oracle.services;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import guru.bonacci.oogway.commons.Gem;
import guru.bonacci.oogway.oracle.GemRepository;

@Controller
public class GemController {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private GemRepository repo;

	@ResponseBody
	@RequestMapping(path = "/givemeagem", method = GET)
	public Optional<Gem> enquire(@RequestParam("q") String q) {
		logger.info("Receiving request for a wise answer on: '" + q + "'");
		return repo.consultTheOracle(q);
	}
}
