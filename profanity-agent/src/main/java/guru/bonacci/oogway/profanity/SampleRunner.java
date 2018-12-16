package guru.bonacci.oogway.profanity;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.SubscribableChannel;


public class SampleRunner {

	//Following sink is used as test consumer for the above processor. It logs the data received through the processor.
	@EnableBinding(TestSink.class)
	static class TestConsumer {

		private final Log logger = LogFactory.getLog(getClass());

		@StreamListener(TestSink.INPUT)
		public void receive(String data) {
			logger.info("Data received..." + data);
		}
	}

	interface TestSink {

		String INPUT = "input1";

		@Input(INPUT)
		SubscribableChannel input1();

	}
}
