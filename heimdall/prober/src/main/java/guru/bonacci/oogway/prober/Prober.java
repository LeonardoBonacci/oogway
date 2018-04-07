package guru.bonacci.oogway.prober;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.oogway.shareddomain.GemCarrier;

@Controller
public class Prober {
	
	@Autowired
	RestTemplate restTemplate;

	@Value("${entrance.service.url}")
	private String serviceUrl;

	@RequestMapping(path = "/shoot", method = GET)
	public void shoot(@RequestParam("q") String q, @RequestParam("apikey") String apiKey) {
		System.out.println("shoot");
		String params = "?q={q}";
		params += "&apikey={apiKey}";

		GemCarrier gem = restTemplate.getForObject(serviceUrl + params, GemCarrier.class, q, apiKey);
		
		System.out.println(gem);
	}

}
