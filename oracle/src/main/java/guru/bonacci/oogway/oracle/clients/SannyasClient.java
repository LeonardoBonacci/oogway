package guru.bonacci.oogway.oracle.clients;

import static org.springframework.http.MediaType.APPLICATION_STREAM_JSON;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import guru.bonacci.oogway.shareddomain.GemCarrier;
import reactor.core.publisher.Flux;

@Component
public class SannyasClient {

	private final WebClient client;
	
	
	public SannyasClient(@Value("${service.sannyas.url}") String serviceUrl) {
		String url = serviceUrl.startsWith("http") ? serviceUrl.trim() : "http://" + serviceUrl.trim();
		client = WebClient.create(url);
	}
	
	@SuppressWarnings("deprecation")
	public Flux<GemCarrier> feed(String searchString) {
		String params = "?q=" + URLEncoder.encode(searchString);
		return client.get()
					.uri("/feed" + params)
					.accept(APPLICATION_STREAM_JSON)
					.retrieve()
					.bodyToFlux(GemCarrier.class);
	}	
}

