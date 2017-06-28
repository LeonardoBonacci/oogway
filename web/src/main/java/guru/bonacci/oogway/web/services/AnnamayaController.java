package guru.bonacci.oogway.web.services;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The outermost layer is known as the annamaya kosha. Anna means 'food' or
 * 'physical matter' and maya means 'made of.'
 */
@Controller
public class AnnamayaController {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private FirstLineSupportService service;

	@RequestMapping("/")
	public String ourOneAndOnlyPage() {
	    return "html/the-html.html";
	}
	
	@ResponseBody
	@RequestMapping(path = "/consult", method = GET)
	public String enquire(@RequestParam("q") String q) {
		logger.info("Receiving request for a wise answer on: '" + q + "'");
		return service.enquire(q);
	}
}
