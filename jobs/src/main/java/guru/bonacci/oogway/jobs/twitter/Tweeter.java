package guru.bonacci.oogway.jobs.twitter;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Component;

@Component
public class Tweeter {

	@Autowired
	Twitter twitterTemplate;

	@Scheduled(cron = "${twitter.cron}")
	public void runForestRun() {
		String a = "The time is now " + LocalDateTime.now();
		System.out.println(a);

		try {
			twitterTemplate.timelineOperations().updateStatus(a);
		} catch (RuntimeException ex) {
			System.out.println("It wasn't ment to be. Unable to tweet" + a);
		}
	}
}