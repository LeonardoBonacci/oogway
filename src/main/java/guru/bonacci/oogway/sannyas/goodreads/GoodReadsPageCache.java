package guru.bonacci.oogway.sannyas.goodreads;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.util.RandomUtils;

@Component
public class GoodReadsPageCache {

    @Cacheable("pages")
    public Integer getPage(String searchURL) {
		int pageNr = 1;
		try {
			Document doc = Jsoup.connect(searchURL).get();
			Elements elements = doc.select("span.gap");
			pageNr = Integer.valueOf(elements.first().nextElementSibling().nextElementSibling().text());
			pageNr = RandomUtils.fromOneInclTo(pageNr);
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
