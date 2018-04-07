package guru.bonacci.oogway.greeter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.oogway.shareddomain.GemCarrier;

@Controller
public class GreetingController {

	@Autowired
	RestTemplate restTemplate;

	@Value("${entrance.service.url}")
	private String serviceUrl;

    @GetMapping("/greeting")
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute Greeting greeting) {
		String params = "?q={q}&apikey={apiKey}";

		String apiKey = greeting.getKey();
		String q = greeting.getQuestion();
		GemCarrier gem = restTemplate.getForObject(serviceUrl + params, GemCarrier.class, q, apiKey);

		greeting.setAnswer(gem.getSaying());
        return "result";
    }

}
