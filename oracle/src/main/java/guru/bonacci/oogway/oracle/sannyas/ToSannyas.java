package guru.bonacci.oogway.oracle.sannyas;

import java.time.Duration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.oracle.beanmapping.GemMapper;
import guru.bonacci.oogway.oracle.clients.SannyasClient;
import guru.bonacci.oogway.oracle.persistence.GemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "service.sannyas.enabled", havingValue = "true")
public class ToSannyas implements Sannyas {

	private final SannyasClient client;

	private final GemRepository repo;

	
	@Override
	public void learn(String q) {
		client.feed(q)
			 .delayElements(Duration.ofSeconds(1)) // for the show...
			 .log()
			 .map(GemMapper.MAPPER::toGem)
			 .flatMap(repo::insert)
			.subscribe(x -> log.info("inserted"));
	}
}
