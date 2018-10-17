package guru.bonacci.oogway.doorway;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.publisher.WorkQueueProcessor;

@Component
@Slf4j
public class SpectreHandler {

	public final WorkQueueProcessor<String> queueProcessor = WorkQueueProcessor.create();
	public final FluxSink<String> sink = queueProcessor.sink();

	@StreamListener(Processor.INPUT)
	public void listen(Message<String> message) {
		String payload = message.getPayload();

		log.info(payload);
		sink.next(payload);
	}

	public Mono<ServerResponse> sink(ServerRequest request) {
		return ok().contentType(TEXT_EVENT_STREAM).body(queueProcessor, String.class);
   	}
}
