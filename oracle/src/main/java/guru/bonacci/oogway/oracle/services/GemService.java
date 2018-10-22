package guru.bonacci.oogway.oracle.services;

import static org.elasticsearch.action.DocWriteResponse.Result.DELETED;
import static org.elasticsearch.action.DocWriteResponse.Result.UPDATED;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import guru.bonacci.oogway.oracle.persistence.Gem;
import guru.bonacci.oogway.oracle.persistence.GemRepository;
import guru.bonacci.oogway.oracle.sannyas.Sannyas;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class GemService {

	private final GemRepository repo;

	private final Sannyas sannyas;
	

    @PreAuthorize("hasRole('write')")
	public Mono<String> create(Gem gem) {
		return repo.insert(gem);
	}

    @PreAuthorize("hasRole('read')")
	public Mono<Gem> retrieve(String id) {
		return repo.findById(id);
	}

    @PreAuthorize("hasRole('read')")
	public Flux<Gem> all() {
		return repo.all();
	}

    @PreAuthorize("hasRole('write')")
	public Mono<Boolean> update(Gem gem) {
		return repo.update(gem)
				   .map(resp -> resp.getResult() == UPDATED)
				   .onErrorReturn(false);
	}

    @PreAuthorize("hasRole('write')")
	public Mono<Boolean> delete(String id) {
		return repo.delete(id)
				   .map(resp -> resp.getResult() == DELETED);
	}

	@PreAuthorize("hasRole('read')")
	public Mono<Gem> searchOne(String q) {
		return repo.findOne(q) // enquiry..
				  .doOnTerminate(() -> sannyas.learn(q)); // ..and learn!!
	}

	@PreAuthorize("hasRole('read')")
	public Flux<Gem> search(String q) {
		return repo.find(q);
	}

    @PreAuthorize("hasRole('read')")
	public Mono<Gem> random() {
		return repo.random(); 
    }	 
}
