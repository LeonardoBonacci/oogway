package guru.bonacci.oogway.jobs.twitter;

import static java.lang.Math.min;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.oracle.client.OracleClient;
import guru.bonacci.oogway.shareddomain.GemCarrier;

@Component
public class Tweeter {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private OracleClient oracleClient;

	@Autowired
	private Twitter twitterTemplate;

	@Scheduled(cron = "${twitter.cron}")
	public void runForestRun() {
		GemCarrier random = oracleClient.findRandom();
		String tweet = random != null ? random.getSaying() : "Now is " + LocalDateTime.now();
		logger.info("tweet: " + tweet);

		try {
			twitterTemplate.timelineOperations().updateStatus(tweet.substring(0, min(tweet.length(), 139)));
		} catch (RuntimeException ex) {
			logger.error("It wasn't meant to be. Unable to tweet: " + tweet, ex);
		}
	}
}