package guru.bonacci.oogway.spectre.geo.service.services;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guru.bonacci.oogway.spectre.geo.service.ip.IPologist;

@Service
public class GeoService {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private IPologist ipologist;
	
	public void index(String ip, String message) {
		logger.info(ipologist.checkUp(ip) + " " + message);
	}
}
