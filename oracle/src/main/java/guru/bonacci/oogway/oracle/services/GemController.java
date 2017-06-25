package guru.bonacci.oogway.oracle.services;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import guru.bonacci.oogway.oracle.api.JMSGem;
import guru.bonacci.oogway.oracle.persistence.GemRepository;
import guru.bonacci.oogway.oracle.persistence.PersistedGem;

@Controller
public class GemController {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private GemRepository repo;

//TODO optional
//	<dependency>
//	  <groupId>com.fasterxml.jackson.datatype</groupId>
//	  <artifactId>jackson-datatype-jdk8</artifactId>
//	  <version>2.6.3</version>
//	</dependency>  
// @ResponseBody
//	@RequestMapping(path = "/givemeagem", method = GET)
//	public Optional<JMSGem> enquire(@RequestParam("q") String q) {
//		logger.info("Receiving request for a wise answer on: '" + q + "'");
//		return repo.consultTheOracle(q);
//	}

	@ResponseBody
	@RequestMapping(path = "/gems", method = GET)
	public PersistedGem enquire(@RequestParam("q") String q) {
		logger.info("Receiving request for a wise answer on: '" + q + "'");
		return repo.consultTheOracle(q).orElse(new PersistedGem("blabla"));
	}	

	@JmsListener(destination = "wisewords")
	public void onMessage(JMSGem wiseWords) {
		logger.info("Receiving an extra bit of knowledge: '" + wiseWords + "'");
		repo.saveTheNewOnly(new PersistedGem(wiseWords.getEssence()));
	}
}
