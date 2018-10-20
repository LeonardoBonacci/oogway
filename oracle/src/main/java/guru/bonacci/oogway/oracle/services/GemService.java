package guru.bonacci.oogway.oracle.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import guru.bonacci.oogway.oracle.persistence.Gem;
import guru.bonacci.oogway.oracle.persistence.GemRepository;
import guru.bonacci.oogway.oracle.sannyas.Sannyas;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
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
}
