package guru.bonacci.oogway.jobs.twitter;

import static java.lang.Math.min;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.jobs.clients.OracleClient;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import reactor.core.publisher.Mono;

@Component
public class Tweeter {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private OracleClient oracleClient;

	@Autowired
	private Twitter twitterTemplate;

	@Scheduled(cron = "${twitter.cron}")
	public void runForrestRun() {
		Mono<GemCarrier> random = oracleClient.random();
		random.map(g -> g.getSaying())
			  .switchIfEmpty(Mono.just(UUID.randomUUID().toString()))
			  .subscribe(this::tweet);
	}
	
	private void tweet(String tweet) {
		logger.info("tweet: " + tweet);

		try {
			twitterTemplate.timelineOperations().updateStatus(tweet.substring(0, min(tweet.length(), 139)));
		} catch (RuntimeException ex) {
			logger.error("It wasn't meant to be. Unable to tweet: " + tweet, ex);
		}
	}
}