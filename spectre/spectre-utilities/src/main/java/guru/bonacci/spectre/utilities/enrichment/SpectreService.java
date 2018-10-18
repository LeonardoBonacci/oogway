package guru.bonacci.spectre.utilities.enrichment;

import reactor.core.publisher.Mono;

public interface SpectreService {

	public Mono<String> enrich(String id);
}
