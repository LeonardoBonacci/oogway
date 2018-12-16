package guru.bonacci.oogway.oracle.prep;


import java.io.IOException;
import java.util.Random;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.domain.GemCarrier;
import guru.bonacci.oogway.oracle.events.Binding;
import guru.bonacci.oogway.utilities.CustomFileUtils;

@Component
public class Indexer {

	private static GemCarrier[] someQuotes = { new GemCarrier("Nothing", "here") };

	
	public Indexer() {
		try {
			//TODO large number of quotes
			someQuotes = CustomFileUtils.readToList("nietzsche.txt").stream()
									.map(quote -> new GemCarrier(quote, "Friedrich Nietzsche"))
									.toArray(GemCarrier[]::new);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@EnableBinding(Binding.class)
	static class Producer {

		private Random random = new Random();

		@Bean
		@InboundChannelAdapter(channel = Binding.OUTPUT, poller = @Poller(fixedDelay = "5000"))
		public MessageSource<String> sendTestData() {
			return () -> {
				int idx = random.nextInt(someQuotes.length);
				return new GenericMessage<>(someQuotes[idx].getSaying());
			};
		}
	}
}
