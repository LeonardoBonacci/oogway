package guru.bonacci.oogway.sannyas.general;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class PageTestCaches {

	@Component
	public class PageTestCache1 implements PageCache {

		int i = 0;
		
	    @Cacheable("pages")
		public Integer getNrOfPages(String searchURL) {
			return ++i;
	    }
	}
	
	@Component
	public class PageTestCache2 implements PageCache {

		int i = 0;
		
	    @Cacheable("pages")
		public Integer getNrOfPages(String searchURL) {
			return ++i;
	    }
	}
}
