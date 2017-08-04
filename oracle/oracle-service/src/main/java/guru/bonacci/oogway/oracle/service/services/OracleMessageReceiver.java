package guru.bonacci.oogway.oracle.service.services;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.oracle.service.persistence.Gem;
import guru.bonacci.oogway.oracle.service.persistence.GemRepository;

@Component
public class OracleMessageReceiver {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private GemRepository repo;

    @StreamListener(OracleSink.CHANNEL_NAME)
	public void onMessage(Wrapper input) {
		logger.info("Receiving an extra bit of knowledge: '" + input + "'");
		repo.saveTheNewOnly(new Gem(input.getContent()));
	}
}
