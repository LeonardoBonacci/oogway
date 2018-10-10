package guru.bonacci.oogway.oracle.services;

import static guru.bonacci.oogway.utilities.CustomFileUtils.readToList;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import guru.bonacci.oogway.oracle.persistence.Gem;
import guru.bonacci.oogway.oracle.persistence.GemRepository;
import guru.bonacci.oogway.oracle.sannyas.Sannyas;
import reactor.core.publisher.Mono;

@Service
public class GemService {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private GemRepository repo;

	@Autowired
	private Sannyas sannyas;
	

    @PreAuthorize("hasRole('read')")
	public Mono<Gem> search(String q) {
		return Mono.justOrEmpty(repo.consultTheOracle(q)) // enquiry..
				  .doOnTerminate(() -> sannyas.learn(q)); // ..and learn!!
	}

	
	@Bean
	CommandLineRunner demo(Environment env, GemRepository repo) {
		return args -> {
			// creative exclusion, is it not?
			if (env.acceptsProfiles("!unit-test")) {
				try {
					Gem[] friedrichsBest = readToList("nietzsche.txt").stream()
																		.map(quote -> new Gem(quote, "Friedrich Nietzsche"))
																		.toArray(Gem[]::new);
					repo.saveTheNewOnly(friedrichsBest);
				} catch (IOException e) {
					logger.error("Nietzsche!!", e);
				}
			}	
		};
	}
}
