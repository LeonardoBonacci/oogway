package guru.bonacci.oogway.doorway;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.slf4j.Logger;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.publisher.WorkQueueProcessor;

@RestController
public class SpectreHandler {

	private final Logger logger = getLogger(this.getClass());

	public final WorkQueueProcessor<String> queueProcessor = WorkQueueProcessor.create();
	public final FluxSink<String> sink = queueProcessor.sink();

	@StreamListener(Processor.INPUT)
	public void listen(Message<String> message) {
		String payload = message.getPayload();

		logger.info(payload);
		sink.next(payload);
	}

	public Mono<ServerResponse> sink(ServerRequest request) {
		return ok().contentType(TEXT_EVENT_STREAM).body(queueProcessor, String.class);
   	}
}
