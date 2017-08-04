package guru.bonacci.oogway.spectre.sentiment.services;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
public class SentimentMessageReceiver {

    @StreamListener(SentimentSink.ENRICHMENT)
	public void onMessage(Wrapper input) {
		System.out.println("sentiment " + input.getUuid());
    }
}
