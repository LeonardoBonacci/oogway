package guru.bonacci.oogway.words;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.SubscribableChannel;


public class JustToShow {

	//Following sink is used as test consumer.
	@EnableBinding(Binding.class)
	static class Consumer {

		private final Log logger = LogFactory.getLog(getClass());

		@StreamListener(Binding.INPUT)
		public void receive(String data) {
			logger.info("Data received..." + data);
		}
	}

	interface Binding {

		String INPUT = "input1";

		@Input(INPUT)
		SubscribableChannel input1();

	}
}
