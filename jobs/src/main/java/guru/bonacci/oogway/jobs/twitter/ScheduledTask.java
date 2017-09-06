package guru.bonacci.oogway.jobs.twitter;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

	@Autowired
	TwitterTemplateCreator templateCreator;

	// @Scheduled(cron = "0 26 8 * * *")
	@Scheduled(cron = "0 * * * * *")
	public void reportCurrentTime() {
		String a = "The time is now " + LocalDateTime.now();
		System.out.println(a);

		try {
			templateCreator.getTwitterTemplate().timelineOperations().updateStatus(a);
		} catch (RuntimeException ex) {
			System.out.println("It wasn't ment to be. Unable to tweet" + a);
		}
	}
}