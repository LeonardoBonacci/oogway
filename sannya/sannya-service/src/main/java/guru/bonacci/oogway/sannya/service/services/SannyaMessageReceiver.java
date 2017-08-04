package guru.bonacci.oogway.sannya.service.services;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannya.service.processing.PitchforkManager;

@Component
public class SannyaMessageReceiver {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private PitchforkManager manager;

    @StreamListener(SannyaSink.CHANNEL_NAME)
	public void onMessage(Wrapper input) {
		logger.info("An opportunity to learn... '" + input.getContent() + "'");
		manager.delegate(input.getContent());
	}
}
