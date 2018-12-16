package guru.bonacci.oogway.profanity;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Processor {

	private final KafkaTemplate<String, Order> template;

	@StreamListener("input")
	public void process(String input) {
		sendMsg(input);
	}

	private Order sendMsg(String name)
	{
        Order order = Order.newBuilder()
        		.setOrderId("OId234")
        		.setCustomerId("CId432")
        		.setSupplierId("SId543")
                .setItems(4)
                .setFirstName(name)
                .setLastName("V")
                .setPrice(178f)
                .setWeight(75f)
                .build();

        template.send("bla", order);
        return order;
	}

}
