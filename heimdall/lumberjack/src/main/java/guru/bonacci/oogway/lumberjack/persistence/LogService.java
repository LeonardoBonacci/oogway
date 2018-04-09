package guru.bonacci.oogway.lumberjack.persistence;

import static java.time.Instant.now;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Service;

@Service
public class LogService {

	// This solution is not thread safe.
	// It is merely a creative solution to a boring exercise.
	private long lastMinutesVisits;

	@Autowired
	private LogRepository repository;

	public long insert(Log logLine) {
		repository.save(logLine);
		return lastMinutesVisits;
	}

	public class LogPersistListener extends AbstractMongoEventListener<Log> {

		@Override
		public void onAfterSave(AfterSaveEvent<Log> event) {
			super.onAfterSave(event);
			lastMinutesVisits = repository.countByApiKeyAndMomentBetween(event.getSource().getApiKey(), 
																		 now().minusSeconds(60), 
																		 now());
		}
	}
}
