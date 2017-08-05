package guru.bonacci.oogway.spectre.sentiment.events;

import static guru.bonacci.oogway.spectre.sentiment.events.SentimentEventChannels.ENRICHMENT;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.secretdomain.GenericEvent;

@Component
public class SentimentRabbitEar {

    @StreamListener(ENRICHMENT)
	public void onMessage(GenericEvent event) {
		System.out.println("sentiment " + event.getContent());
    }
}
