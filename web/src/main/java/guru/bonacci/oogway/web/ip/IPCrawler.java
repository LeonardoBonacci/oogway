package guru.bonacci.oogway.web.ip;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class IPCrawler {

	public static final String serviceURL = "http://sqa.fyicenter.com/Online_Test_Tools/Test_IP_Address_Generator.php";

	private static final int DEFAULT_SIZE = 12;

	public List<String> crawl() throws IOException {
		return crawl(DEFAULT_SIZE);
	}
	
	public List<String> crawl(int count) throws IOException {
        Response response = 
                Jsoup.connect(serviceURL)
                .userAgent("Mozilla")
                .method(Method.POST)
                .data("Count", Integer.toString(count))
                .data("Submit", "Start")
                .execute();
        
		Element textarea = response.parse().select("textarea").first();
		return Stream.of(textarea.text().split("\n")).collect(toList());
	}
}
