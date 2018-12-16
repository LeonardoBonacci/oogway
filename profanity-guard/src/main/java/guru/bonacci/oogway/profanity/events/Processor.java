package guru.bonacci.oogway.profanity.events;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.profanity.domain.Gem;
import guru.bonacci.oogway.profanity.filter.ProfanityFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Slf4j
@Component
public class Processor {

	@Value("${topic.name}")
	private String topic;

	private final KafkaTemplate<String, Gem> template;
	private final ProfanityFilter filter;


	@StreamListener
	public void process(@Input(Binding.INPUT) Flux<String> input) {
		input.filter(filter::test)
			 .subscribe(quote -> send(quote));
	}

	private void send(String name) {
        Gem quote = Gem.newBuilder()
        		.setSaying(name)
        		.setAuthor("constant")
                .build();

        template.send(topic, quote);
        log.debug(quote + " - sent");
	}

}
