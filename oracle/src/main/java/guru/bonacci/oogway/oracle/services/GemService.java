package guru.bonacci.oogway.oracle.services;

import static guru.bonacci.oogway.utilities.CustomFileUtils.readToList;

import java.io.IOException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import guru.bonacci.oogway.oracle.persistence.Gem;
import guru.bonacci.oogway.oracle.persistence.GemRepository;
import guru.bonacci.oogway.oracle.sannyas.Sannyas;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class GemService {

	private final GemRepository repo;

	private final Sannyas sannyas;
	

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
								.flatMap(repo::insert)
								.subscribe(x -> log.info("inserted"));
			} catch (IOException e) {
				log.error("Nietzsche!!", e);
			}
		};
	}
}
