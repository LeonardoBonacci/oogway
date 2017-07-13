package guru.bonacci.oogway.spectre.geo.service.services;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class GeoService {

	private final Logger logger = getLogger(this.getClass());

	public void index(String ip, String message) {
		logger.info(ip + " " + message);
	}
}
