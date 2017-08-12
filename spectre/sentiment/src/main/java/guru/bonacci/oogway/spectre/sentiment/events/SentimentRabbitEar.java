package guru.bonacci.oogway.spectre.sentiment.events;

import static guru.bonacci.oogway.spectre.sentiment.events.SentimentEventChannels.ENRICHMENT;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.shareddomain.GenericEvent;
import guru.bonacci.oogway.spectre.sentiment.services.SentimentService;

@Component
public class SentimentRabbitEar {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private SentimentService service;

	@StreamListener(ENRICHMENT)
	public void onMessage(GenericEvent event) {
		logger.info("Incoming... " + event.getContent());

		service.enrich(event.getContent());
	}	
}
