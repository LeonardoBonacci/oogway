package guru.bonacci.oogway.sannyas;

import org.springframework.cache.annotation.Cacheable;

public interface PageCache {

    @Cacheable("pages")
	Integer getNrOfPages(String searchURL);
}
