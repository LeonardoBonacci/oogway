package guru.bonacci.oogway.spectre.geo.service.services;

import static java.util.UUID.randomUUID;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guru.bonacci.oogway.shareddomain.GenericEvent;
import guru.bonacci.oogway.spectre.geo.service.event.EnrichmentGateway;
import guru.bonacci.oogway.spectre.geo.service.ip.IIPologist;

@Service
public class GeoService {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
    private EnrichmentGateway gateway;

	@Autowired
	private IIPologist ipologist;
	
	public void index(String ip, String message) {
		UUID uuid = randomUUID();
		logger.info(ipologist.checkUp(ip) + " " + uuid + " " + message);

		try {
			Thread.sleep(3000);
//			gateway.send(new GenericEvent(uuid.toString()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
