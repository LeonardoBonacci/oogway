package guru.bonacci.oogway.profanity;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.profanity.domain.Gem;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Processor {

	private final KafkaTemplate<String, Gem> template;

	@StreamListener("input")
	public void process(String input) {
		send(input);
	}

	private void send(String name)
	{
        Gem quote = Gem.newBuilder()
        		.setSaying(name)
        		.setAuthor("constant")
                .build();

        template.send("blaas", quote);
        System.out.println(quote + " - sent");
	}

}
