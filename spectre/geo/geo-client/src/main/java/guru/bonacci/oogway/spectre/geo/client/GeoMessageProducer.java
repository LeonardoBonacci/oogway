package guru.bonacci.oogway.spectre.geo.client;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.spectre.geo.api._1984;

/**
 * Talks to Geo via messaging
 */
@Component
public class GeoMessageProducer {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
    private SpectreGateway spectreGateway;

	public void send(String ip, String message) {        
		spectreGateway.generate(new _1984(ip, message));
	}
}
