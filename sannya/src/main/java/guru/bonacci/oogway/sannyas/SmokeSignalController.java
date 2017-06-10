package guru.bonacci.oogway.sannyas;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import guru.bonacci.oogway.jms.JMSConfig;

@Controller
public class SmokeSignalController {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private PitchforkManager manager;

	@JmsListener(destination = JMSConfig.QUEUE)
	public void onMessage(String input) {
		logger.info("Received message <" + input + ">");
		manager.delegate(input);
	}
	
	@ResponseBody
	@RequestMapping(path = "/backdoor", method = RequestMethod.POST)
	public void index(@RequestBody String input) {
		logger.info("receiving backdoor index request for: '" + input + "'");
		manager.delegate(input);
	}
}
