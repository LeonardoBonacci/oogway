package guru.bonacci.oogway.spectre.geo.service.services;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.spectre.geo.api._1984;

@Component
public class GeoMessageReceiver {

	private final Logger logger = getLogger(this.getClass());

	@JmsListener(destination = "${spring.activemq.queue.to-geo}")
	public void onMessage(_1984 _1984) {
		logger.info(_1984.getIP() + " " + _1984.getMessage());
	}
}
