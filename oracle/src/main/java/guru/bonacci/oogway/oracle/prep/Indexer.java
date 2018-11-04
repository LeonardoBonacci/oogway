package guru.bonacci.oogway.oracle.prep;

import static guru.bonacci.oogway.oracle.persistence.GemRepository.INDEX;
import static guru.bonacci.oogway.oracle.persistence.GemRepository.PIPELINE;
import static guru.bonacci.oogway.oracle.persistence.GemRepository.TYPE;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

import javax.annotation.PostConstruct;

import org.elasticsearch.action.index.IndexResponse;
import org.springframework.stereotype.Service;

import guru.bonacci.oogway.oracle.persistence.Gem;
import guru.bonacci.oogway.relastic.ElasticAdapter;
import guru.bonacci.oogway.relastic.ElasticAdminAdapter;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class Indexer {

	private final GemGenerator generator;
	private final ElasticAdapter<Gem> elastic;
	private final ElasticAdminAdapter elasticAdmin;
	
	private final Timer indexTimer = Metrics.timer("es.timer");
    private final LongAdder concurrent = Metrics.gauge("es.concurrent", new LongAdder());
    private final Counter successes = Metrics.counter("es.index", "result", "success");
	private final Counter failures = Metrics.counter("es.index", "result", "failure");
	
	public Indexer(GemGenerator generator, ElasticAdapter<Gem> elastic, ElasticAdminAdapter elasticAdmin) {
		this.generator = generator;
		this.elastic = elastic.theIndex(INDEX).theType(TYPE).thePipeline(PIPELINE).theGenericType(Gem.class);
		this.elasticAdmin = elasticAdmin;
    }

	
	private Flux<IndexResponse> index(int count, int concurrency) {
		return generator
	            .infinite()
	            .take(count)
	            .flatMap(doc -> countConcurrent(measure(indexDocSwallowErrors(doc))), concurrency);
    }
	

	private <T> Mono<T> countConcurrent(Mono<T> input) {
	        return input
	                .doOnSubscribe(s -> concurrent.increment())
	                .doOnTerminate(concurrent::decrement);
	}
	
	private <T> Mono<T> measure(Mono<T> input) {
        return Mono
                .fromCallable(System::currentTimeMillis)
                .flatMap(time ->
                        input.doOnSuccess(x -> indexTimer.record(System.currentTimeMillis() - time, TimeUnit.MILLISECONDS))
                );
    }

	private Mono<IndexResponse> indexDocSwallowErrors(Gem gem) {
        return elastic.indexDoc(gem)
                .doOnSuccess(response -> successes.increment())
                .doOnError(e -> log.error("Unable to index {}", gem, e))
                .doOnError(e -> failures.increment())
                .onErrorResume(e -> Mono.empty());
	}

	@PostConstruct
    void startIndexing() {
    	String receivedPipeline =
		    "{\"description\":\"my set of processors\"," +
		        "\"processors\":[{\"set\":{\"field\":\"received\",\"value\":\"{{_ingest.timestamp}}\"}}]}";
		elasticAdmin.putPipeline(PIPELINE, receivedPipeline)
			   .subscribe(resp -> {
				   log.warn("Pipeline acknowledged {}", resp.isAcknowledged());
				   Flux
	                .range(0, 21)
	                .map(x -> 4) //or do something more intelligent..
	                .doOnNext(x -> log.debug("Target concurrency: {}", x))
	                .concatMap(concurrency -> index(21, concurrency))
	                .window(Duration.ofSeconds(1))
	                .flatMap(Flux::count)
	                .subscribe(winSize -> log.debug("Got {} responses in last second", winSize));
			   });
	}
}
