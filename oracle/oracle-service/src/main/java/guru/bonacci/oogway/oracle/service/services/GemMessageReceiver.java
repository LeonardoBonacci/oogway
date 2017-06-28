package guru.bonacci.oogway.oracle.service.services;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.oracle.service.persistence.Gem;
import guru.bonacci.oogway.oracle.service.persistence.GemRepository;

@Component
public class GemMessageReceiver {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private GemRepository repo;

	@JmsListener(destination = "${spring.activemq.queue.to-oracle}")
	public void onMessage(String input) {
		logger.info("Receiving an extra bit of knowledge: '" + input + "'");
		repo.saveTheNewOnly(new Gem(input));
	}
}
