package guru.bonacci.oogway.sannya.service.services;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannya.service.processing.PitchforkManager;

/**
 * Previously called: SmokeSignalMessageReceiver:
 * 
 * The smoke signal is one of the oldest forms of long-distance communication.
 * It is a form of visual communication used over long distance. In general
 * smoke signals are used to transmit news, signal danger, or gather people to a
 * common area.
 */
@Component
public class OurFirstMessageReceiver {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private PitchforkManager manager;

	@JmsListener(destination = "${spring.activemq.queue.to-sannya}")
	public void onMessage(String input) {
		logger.info("An opportunity to learn... '" + input + "'");
		manager.delegate(input);
	}
}
