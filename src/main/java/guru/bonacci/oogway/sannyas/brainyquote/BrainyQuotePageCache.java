package guru.bonacci.oogway.sannyas.brainyquote;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannyas.PageCache;

@Component
public class BrainyQuotePageCache implements PageCache {

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
}
