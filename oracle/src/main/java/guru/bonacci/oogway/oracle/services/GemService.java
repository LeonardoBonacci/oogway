package guru.bonacci.oogway.oracle.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import guru.bonacci.oogway.oracle.persistence.Gem;
import guru.bonacci.oogway.oracle.persistence.GemRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class GemService {

	private final GemRepository repo;


	@PreAuthorize("hasRole('read')")
	public Mono<Gem> searchOne(String q) {
		return repo.findOne(q);
	}
}
