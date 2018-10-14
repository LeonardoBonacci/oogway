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
import reactor.core.publisher.Flux;
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
		return repo.find(q) // enquiry..
				  .doOnTerminate(() -> sannyas.learn(q)); // ..and learn!!
	}

    @PreAuthorize("hasRole('read')")
	public Mono<Gem> random() {
		return repo.random(); 
    }	 

    
	@Bean
	CommandLineRunner demo(Environment env, GemRepository repo) {
		return args -> {
			try {
				Flux.fromIterable(readToList("nietzsche.txt"))
								.map(quote -> Gem.builder().saying(quote).author("Friedrich Nietzsche").build())
								.flatMap(repo::upsert)
								.subscribe(x -> logger.info("inserted"));
			} catch (IOException e) {
				logger.error("Nietzsche!!", e);
			}
		};
	}
}
