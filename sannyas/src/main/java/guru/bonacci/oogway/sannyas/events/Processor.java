package guru.bonacci.oogway.sannyas.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.domain.GemCarrier;
import guru.bonacci.oogway.domain.GenericEvent;
import guru.bonacci.oogway.sannyas.services.SannyasService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Slf4j
@Component
public class Processor {

	private final SannyasService service;
	
	
	@StreamListener
	@Output(Binding.OUTPUT)
	public Flux<Message<String>> onMessage(@Input(Binding.INPUT) Flux<GenericEvent> event) {
		return event.map(GenericEvent::getContent)
					.doOnEach(s -> log.info("in " + s.get()))
					.flatMap(service::feed)
					.map(GemCarrier::getSaying)
					.doOnEach(s -> log.info("and out " + s.get()))
					.map(m -> MessageBuilder.withPayload(m).build());
	} 
}
