package guru.bonacci.oogway.sannya.service.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class InternetProvider {

	boolean proxyEnabled = true;
	String host = "127.0.0.1";
	int port = 9050;
	Proxy.Type proxyType = Proxy.Type.valueOf("SOCKS");

	public URLConnection connectMe(String url) throws IOException {
		URLConnection connection = provideConnection(new URL(url));
		connection.connect();
		return connection;
	}

	public URLConnection provideConnection(URL url) throws IOException {
		Proxy proxy = provideProxy(); 
		
		HttpURLConnection uc = proxy != null ? 
				(HttpURLConnection)url.openConnection(proxy) : 
				(HttpURLConnection)url.openConnection();

		uc.setRequestProperty("User-Agent", "Mozilla");
		uc.setRequestMethod("GET");

		return uc;
	}	
	
	public Proxy provideProxy() {
		return proxyEnabled ? new Proxy(proxyType, new InetSocketAddress(host, port)) : null;
	}
}
