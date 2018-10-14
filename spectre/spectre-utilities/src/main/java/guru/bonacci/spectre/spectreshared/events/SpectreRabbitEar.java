package guru.bonacci.spectre.spectreshared.events;

import static guru.bonacci.spectre.spectreshared.events.SpectreStreams.ENRICHMENT;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import guru.bonacci.oogway.shareddomain.GenericEvent;
import guru.bonacci.spectre.spectreutilities.enrichment.SpectreService;
import reactor.core.publisher.Flux;

public abstract class SpectreRabbitEar {

	private final SpectreService service;
	
	public SpectreRabbitEar(SpectreService service) {
		this.service = service;
	}

	@StreamListener
	@Output(Processor.OUTPUT)
	public Flux<Message<String>> onMessage(@Input(ENRICHMENT) Flux<GenericEvent> event) {
		return event.map(GenericEvent::getContent)
					.flatMap(service::enrich)
					.map(m -> MessageBuilder.withPayload(m).build());
	}	
}
