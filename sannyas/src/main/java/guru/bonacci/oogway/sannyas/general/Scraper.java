package guru.bonacci.oogway.sannyas.general;

import guru.bonacci.oogway.shareddomain.GemCarrier;
import reactor.core.publisher.Flux;

/**
 * Scraper:
 * a tool or device used for scraping, especially for removing dirt, paint, 
 * or other unwanted matter from a surface.
 */
public interface Scraper {

	Flux<GemCarrier> find(String... tags);
}
