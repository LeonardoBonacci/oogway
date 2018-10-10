package guru.bonacci.oogway.lumberjack.persistence;

import java.time.Instant;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface LogRepository extends ReactiveMongoRepository<Log, String> {

	Flux<Log> findByMomentBetween(Instant from, Instant until);

	Mono<Long> countByMomentBetween(Instant from, Instant until);
	
	Flux<Log> findByApiKeyAndMomentBetween(String apiKey, Instant from, Instant until);

	Mono<Long> countByApiKeyAndMomentBetween(String apiKey, Instant from, Instant until);
}
