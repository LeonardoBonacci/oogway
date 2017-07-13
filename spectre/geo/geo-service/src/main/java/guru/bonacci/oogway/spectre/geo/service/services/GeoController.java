package guru.bonacci.oogway.spectre.geo.service.services;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.spectre.geo.api._1984;

@RestController
public class GeoController {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private GeoService service;
	
	@RequestMapping(path = "/backdoor", method = POST)
	public void log(@RequestBody _1984 _1984) {
		logger.info("Receiving secret request to process: '" + _1984 + "'");
		service.index(_1984.getIP(), _1984.getMessage());
	}

	@RequestMapping(path = "/version", method = GET)
	public String version(@Value("${build.version}") String buildVersion) {
		return buildVersion;
	}	
}
