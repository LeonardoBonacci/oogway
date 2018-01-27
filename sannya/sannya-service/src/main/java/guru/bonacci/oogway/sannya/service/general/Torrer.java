package guru.bonacci.oogway.sannya.service.general;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Torrer {

	public static void main(String[] args) {
		try {
			URL url = new URL("https://www.goodreads.com/");
			Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 9050)); 

			HttpURLConnection uc = (HttpURLConnection)url.openConnection(proxy);
			uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
			uc.setRequestProperty("Content-Language", "en-US");
			uc.setRequestMethod("GET");
			uc.connect();
		
			uc.getInputStream();
			Document doc = Jsoup.parse(uc.getInputStream(), null, "not-needed");
			System.out.println(doc.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
