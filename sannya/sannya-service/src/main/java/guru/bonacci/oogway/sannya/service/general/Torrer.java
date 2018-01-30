package guru.bonacci.oogway.sannya.service.general;

import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import guru.bonacci.oogway.sannya.service.http.InternetProvider;

public class Torrer {

	public static void main(String[] args) {
		String url = "https://www.quotesdaddy.com/find/quote/jesus/1";
//		url = "https://www.goodreads.com/quotes/tag/tree";
		try {
			URLConnection uc = new InternetProvider().connectMe(url);
			Document doc = Jsoup.parse(uc.getInputStream(), null, "not-needed");
			System.out.println(doc.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
