package guru.bonacci.oogway.oracle.prep;

import static guru.bonacci.oogway.utilities.CustomFileUtils.readToList;

import java.io.IOException;
import java.util.Iterator;

import org.springframework.stereotype.Component;

import guru.bonacci.oogway.oracle.persistence.Gem;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Component
class GemGenerator {

    private final Scheduler scheduler = Schedulers.newParallel(GemGenerator.class.getSimpleName());
    private Iterator<String> nietzsches;

    GemGenerator() {
		try {
			nietzsches = readToList("nietzsche.txt").iterator();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    Flux<Gem> infinite() {
        return generateOne().repeat();
    }

    private Mono<Gem> generateOne() {
        return Mono
                .fromCallable(this::quote)
                .subscribeOn(scheduler);
    }

    private Gem quote() {
    	return nietzsches.hasNext() ? Gem.builder().saying(nietzsches.next()).author("Friedrich Nietzsche").build() : null;
    }
}
