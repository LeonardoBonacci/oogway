package guru.bonacci.spectre.spectreutilities.enrichment;

import reactor.core.publisher.Mono;

public interface SpectreService {

	public Mono<String> enrich(String id);
}
