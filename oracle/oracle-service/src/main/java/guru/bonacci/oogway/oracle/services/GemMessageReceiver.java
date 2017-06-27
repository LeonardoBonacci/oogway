package guru.bonacci.oogway.oracle.services;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.oracle.persistence.Gem;
import guru.bonacci.oogway.oracle.persistence.GemRepository;

@Component
public class GemMessageReceiver {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private GemRepository repo;

	@JmsListener(destination = "wisewords")
	public void onMessage(String input) {
		logger.info("Receiving an extra bit of knowledge: '" + input + "'");
		repo.saveTheNewOnly(new Gem(input));
	}
}