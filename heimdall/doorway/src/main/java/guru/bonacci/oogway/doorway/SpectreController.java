package guru.bonacci.oogway.doorway;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.WorkQueueProcessor;

@RestController
public class SpectreController {

	private final Logger logger = getLogger(this.getClass());

	public final WorkQueueProcessor<String> queueProcessor = WorkQueueProcessor.create();
	public final FluxSink<String> sink = queueProcessor.sink();

	@StreamListener(Processor.INPUT)
	public void onPong(Message<String> message) {
		String payload = message.getPayload();

		logger.info(payload);
		sink.next(payload);
	}

	@GetMapping(value = "/spectre", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> serverSendEvents() {
		return queueProcessor;
	}
}
