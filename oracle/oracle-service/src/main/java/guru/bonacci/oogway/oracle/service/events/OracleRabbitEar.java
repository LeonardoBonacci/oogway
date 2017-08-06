package guru.bonacci.oogway.oracle.service.events;

import static guru.bonacci.oogway.oracle.service.events.OracleEventChannels.SANNYA;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.oracle.service.persistence.Gem;
import guru.bonacci.oogway.oracle.service.persistence.GemRepository;
import guru.bonacci.oogway.shareddomain.GemDTO;

@Component
public class OracleRabbitEar {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private GemRepository repo;

    @StreamListener(SANNYA)
	public void onMessage(GemDTO event) {
		logger.info("Receiving an extra bit of knowledge: '" + event + "'");
		repo.saveTheNewOnly(new Gem(event.getSaying(), event.getAuthor()));
	}
}
