package guru.bonacci.oogway.oracle.sannyas;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.oracle.beanmapping.GemMapper;
import guru.bonacci.oogway.oracle.clients.SannyasClient;
import guru.bonacci.oogway.oracle.persistence.GemRepository;

@Component
@ConditionalOnProperty(name = "service.sannyas.enabled", havingValue = "true")
public class ToSannyas implements Sannyas {

	@Autowired
	private SannyasClient client;

	@Autowired
	private GemRepository repo;

	@Override
	public void learn(String q) {
		client.feed(q)
			 .delayElements(Duration.ofSeconds(1)) // for the show...
			 .log()
			 .map(GemMapper.MAPPER::toGem)
			 .subscribe(repo::save);
	}
}
