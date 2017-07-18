package guru.bonacci.oogway.spectre.sentiment.services;

import java.io.IOException;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class SentimentMessageReceiver {

	String previousInput;

	/**
	 * Avoiding Duplicate Message in a Network of Brokers
	 * 
	 * You have to make sure that the messages sent to the
	 * Consumer.*.VirtualTopic.> destination are not forwarded when you're using
	 * both queue-based and non-queue based subscribers to the virtual topic
	 * (that is, if you have normal topic subscribers to the virtual topic). If
	 * you use Virtual Topics in a network of brokers, it is likely you will get
	 * duplicate messages if you use the default network configuration. This is
	 * because a network node will not only forward message sent to the virtual
	 * topic, but also the associated physical queues. To fix this, you should
	 * disable forwarding messages on the associated physical queues.
	 * 
	 * Here is an example of how to do that: <networkConnectors>
	 * <networkConnector uri="static://(tcp://localhost:61617)">
	 * <excludedDestinations> <queue physicalName="Consumer.*.VirtualTopic.>"/>
	 * </excludedDestinations> </networkConnector> </networkConnectors>
	 * 
	 * @param input
	 * @throws IOException
	 */
	@JmsListener(destination = "Consumer.Sentiment.VirtualTopic.first-topic")
	public void onMessage(String input) throws IOException {
		// it hurts my heart to see this, but for now it prevents double
		// processing
		if (input == null || input.equals(previousInput))
			return;
		else
			previousInput = input;

		System.out.println(input);
	}
}
