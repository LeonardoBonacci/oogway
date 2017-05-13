package guru.bonacci.oogway.sannyas.brainyquote;


import java.io.IOException;

import org.apache.commons.lang3.RandomUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannyas.general.PageCache;
import guru.bonacci.oogway.sannyas.general.WebIlluminator;

/**
 * Quote of the day:
 * “I want to do to you what spring does with the cherry trees.” ― Pablo Neruda
 */
@Component
public class BrainyQuoteIlluminator extends WebIlluminator implements PageCache {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String URL = "https://www.brainyquote.com/quotes/keywords/#tag#_#page#.html?vm=l";
	
	@Override
	protected String determineURL(String searchStr) {
		String searchURL = URL.replace("#tag#", searchStr);
		Integer nrOfPages = getNrOfPages(searchURL.replace("#page#", "1"));
		return searchURL.replace("#page#", String.valueOf(RandomUtils.nextInt(1, nrOfPages + 1)));
	}

	@Override
	public Integer getNrOfPages(String searchURL) {
		Integer pageNr = 1;
		try {
			Document doc = Jsoup.connect(searchURL).userAgent("Mozilla").get();
			Elements elements = doc.select("ul.pagination a");
			pageNr = Integer.valueOf(elements.get(elements.size() - 2).text()); //last page is second last element
		} catch (Exception e) { 
			// Taking the easy way, catching all exceptions.
			// No results or one result: page 1
			// Not enough results for a gap between the pagination numbers: page 1
			// More than two results: page x
			e.printStackTrace();
		}
		return pageNr;
    }

	@Override
	protected Elements consultWeb(String searchURL) {
		try {
			Document doc = Jsoup.connect(searchURL).userAgent("Mozilla").get();
			return doc.select("a[title='view quote']");
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return new Elements();
	}
}
