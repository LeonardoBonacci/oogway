package guru.bonacci.oogway.spectre.geo.service.services;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guru.bonacci.oogway.spectre.geo.service.ip.IIPologist;

@Service
public class GeoService {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private IIPologist ipologist;
	
	public void index(String ip, String message) {
		logger.error(ipologist.checkUp(ip) + " " + message);
	}
}
