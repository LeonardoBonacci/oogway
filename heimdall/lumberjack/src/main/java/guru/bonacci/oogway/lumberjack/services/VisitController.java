package guru.bonacci.oogway.lumberjack.services;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VisitController {
	
	@RequestMapping(path = "/visit", method = GET)
	public void log(@RequestParam("apikey") String apiKey) {
	}
}
