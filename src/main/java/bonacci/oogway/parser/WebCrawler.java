package bonacci.oogway.parser;

import java.io.IOException;

import org.jsoup.select.Elements;

public interface WebCrawler {

	Elements crawl(String searchStr) throws IOException;
}
