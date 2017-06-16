package guru.bonacci.oogway.sannyas.services;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The smoke signal is one of the oldest forms of long-distance communication.
 * It is a form of visual communication used over long distance. In general
 * smoke signals are used to transmit news, signal danger, or gather people to a
 * common area.
 * 
 * It is here that the service receives its messages.
 */
@Controller
public class SmokeSignalController {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private PitchforkManager manager;

	@JmsListener(destination = "${spring.activemq.queue.name}")
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
