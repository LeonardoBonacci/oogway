package guru.bonacci.oogway.profanity.events;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.domain.GemCarrier;
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
	public void process(@Input(Binding.INPUT) Flux<GemCarrier> input) {
		input.filter(gem -> filter.test(gem.getSaying()))
			 .subscribe(quote -> send(quote));
	}

	private void send(GemCarrier gemC) {
        Gem gem = Gem.newBuilder()
        		.setSaying(gemC.getSaying())
        		.setAuthor(gemC.getAuthor())
                .build();

        template.send(topic, gem);
        log.debug(gem + " - sent");
	}

}
