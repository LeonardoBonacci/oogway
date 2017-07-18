package guru.bonacci.oogway.spectre.localtimer.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class LocalTimerMessageReceiver {

	@Autowired
	SpecRepository repo;

	@Autowired
	ElasticsearchTemplate template;

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
	@JmsListener(destination = "Consumer.LocalTimer.VirtualTopic.first-topic")
	public void onMessage(String input) throws IOException {
		// it hurts my heart to see this, but for now it prevents double
		// processing
		if (input == null || input.equals(previousInput))
			return;
		else
			previousInput = input;

		System.out.println(input);
//		Spec s = repo.findOne(input);

		// UpdateRequest updateRequest = new UpdateRequest();
		// updateRequest.index("logstash-spectre");
		// updateRequest.type("logs");
		// updateRequest.id(s.getId());
		// updateRequest.doc(jsonBuilder().startObject().field("title", "new
		// title").endObject());
		// UpdateQuery updateQuery = new
		// UpdateQueryBuilder().withId(s.getId()).withClass(Spec.class).withUpdateRequest(updateRequest).build();
		// template.update(updateQuery);
		//
		// repo.save(s);
	}
}
