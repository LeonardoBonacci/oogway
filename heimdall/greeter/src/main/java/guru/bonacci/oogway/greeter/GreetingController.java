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

	private final String serviceUrl;

	public GreetingController(@Value("${doorway.service.url}") String serviceUrl) {
		this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl.trim() : "http://" + serviceUrl.trim();
	}
	
    @GetMapping("/greeting")
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "greeting";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute Greeting greeting) {
		String params = "/iam/{apikey}?q={q}";

		String apikey = greeting.getKey();
		String q = greeting.getQuestion();
		GemCarrier gem = restTemplate.getForObject(serviceUrl + params, GemCarrier.class, apikey, q);

		greeting.setKey(gem.getAuthor());
		greeting.setAnswer(gem.getSaying());
        return "result";
    }

}
