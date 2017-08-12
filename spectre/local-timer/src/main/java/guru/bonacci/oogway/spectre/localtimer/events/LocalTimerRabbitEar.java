package guru.bonacci.oogway.spectre.localtimer.events;

import static guru.bonacci.oogway.spectre.localtimer.events.LocalTimerEventChannels.ENRICHMENT;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.shareddomain.GenericEvent;
import guru.bonacci.oogway.spectre.localtimer.services.LocalTimerService;

@Component
public class LocalTimerRabbitEar {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private LocalTimerService service;

	@StreamListener(ENRICHMENT)
	public void onMessage(GenericEvent event) {
		logger.info("Incoming... " + event.getContent());

		service.enrich(event.getContent());
	}	
}
