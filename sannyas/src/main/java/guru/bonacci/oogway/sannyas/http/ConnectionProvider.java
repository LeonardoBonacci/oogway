package guru.bonacci.oogway.sannyas.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Component;

@Component
public class ConnectionProvider implements IConnectionProvider {

	public HttpURLConnection provideConnection(URL url) throws IOException {
		return (HttpURLConnection)url.openConnection();
	}	
}	
