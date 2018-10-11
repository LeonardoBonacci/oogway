package guru.bonacci.spectre.spectreshared.events;

import static guru.bonacci.spectre.spectreshared.events.SpectreStreams.ENRICHMENT;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;

import guru.bonacci.oogway.shareddomain.GenericEvent;
import guru.bonacci.spectre.spectreutilities.enrichment.SpectreService;
import reactor.core.publisher.Flux;

public abstract class SpectreRabbitEar {

	private final SpectreService service;
	
	public SpectreRabbitEar(SpectreService service) {
		this.service = service;
	}

	@StreamListener
	public void onMessage(@Input(ENRICHMENT) Flux<GenericEvent> event) {
		event.map(GenericEvent::getContent)
			.flatMap(service::enrich)
			.subscribe();
	}	
}
