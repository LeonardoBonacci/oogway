package guru.bonacci.oogway.lumberjack.services;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class VisitController {
	
	@Autowired
	RestTemplate restTemplate;

	@Value("${entrance.service.url}")
	private String serviceUrl;

	@RequestMapping(path = "/visit", method = GET)
	public void log(@RequestParam("apikey") String apiKey) {
		System.out.println("shoot");
		String params = "?q={q}";
		params += "&apikey={apiKey}";

		
	}

}
