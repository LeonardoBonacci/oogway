package guru.bonacci.oogway.entrance.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LumberjackClientForTesting {

	@Autowired
	private RestTemplate restTemplate;

	private final String serviceUrl;

	public LumberjackClientForTesting(@Value("${lumberjack.service.url}") String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl.trim() : "http://" + serviceUrl.trim();
	}

	public Long visits(String apiKey) {
		Long visits = restTemplate.getForObject(serviceUrl + "/visits/{apiKey}", Long.class, apiKey);
		return visits;
	}
}

