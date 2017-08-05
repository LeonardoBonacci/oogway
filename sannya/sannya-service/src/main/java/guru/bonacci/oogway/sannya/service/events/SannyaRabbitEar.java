package guru.bonacci.oogway.sannya.service.events;

import static guru.bonacci.oogway.sannya.service.events.SannyaEventChannels.ORACLE;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannya.service.processing.PitchforkManager;
import guru.bonacci.oogway.secretdomain.GenericEvent;

@Component
public class SannyaRabbitEar {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private PitchforkManager manager;

    @StreamListener(ORACLE)
	public void onMessage(GenericEvent event) {
		logger.info("An opportunity to learn... '" + event.getContent() + "'");
		manager.delegate(event.getContent());
	}
}
