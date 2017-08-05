package guru.bonacci.oogway.spectre.sentiment.events;

import static guru.bonacci.oogway.spectre.sentiment.events.SentimentEventChannels.ENRICHMENT;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
public class SentimentRabbitEar {

    @StreamListener(ENRICHMENT)
	public void onMessage(Wrapper input) {
		System.out.println("sentiment " + input.getUuid());
    }
}
