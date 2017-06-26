package guru.bonacci.oogway.sannyas.services;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;

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
}
