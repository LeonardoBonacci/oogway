package guru.bonacci.oogway.doorway;


import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import guru.bonacci.oogway.domain.GenericEvent;

public class SampleRunner {

	//Following code is only used as a test harness.

	//Following source is used as test producer.
	@EnableBinding(TestSource.class)
	static class TestProducer {

		private AtomicBoolean semaphore = new AtomicBoolean(true);

		private String[] randomWords = new String[]{"love", "tree", "peace", "jesus"};
		private Random random = new Random();

		@Bean
		@InboundChannelAdapter(channel = TestSource.OUTPUT, poller = @Poller(fixedDelay = "1000"))
		public MessageSource<GenericEvent> sendTestData() {
			return () -> {
				int idx = random.nextInt(5);
				return new GenericMessage<>(new GenericEvent(randomWords[idx]));
			};
		}
	}

	interface TestSource {

		String OUTPUT = "output1";

		@Output(TestSource.OUTPUT)
		MessageChannel output();

	}
}
