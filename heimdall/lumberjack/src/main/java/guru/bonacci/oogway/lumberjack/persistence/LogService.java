package guru.bonacci.oogway.lumberjack.persistence;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class LogService {

	// This solution is not thread safe
	// It is merely a creative exercise
	private long nrOfVisitsInlastMinute;

	@Autowired
	private LogRepository repository;

	public long insert(Log logLine) {
		repository.save(logLine);
		return nrOfVisitsInlastMinute;
	}

	@Component
	public class MongoPersistListener extends LoggingEventListener {
		
		@Override
		public void onAfterSave(AfterSaveEvent<Object> event) {
			super.onAfterSave(event);
			Log logLine = (Log)event.getSource();
			nrOfVisitsInlastMinute = repository.countByApiKeyAndMomentBetween(logLine.getApiKey(), Instant.now().minusSeconds(60), Instant.now());
		}
	}
}
