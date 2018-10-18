package guru.bonacci.spectre.sentiment.events;

import org.springframework.stereotype.Component;

import guru.bonacci.spectre.sentiment.services.SentimentService;
import guru.bonacci.spectre.utilities.events.SpectreRabbitEar;

@Component
public class SentimentRabbitEar extends SpectreRabbitEar {


	public SentimentRabbitEar(SentimentService service) {
		super(service);
	}
}
