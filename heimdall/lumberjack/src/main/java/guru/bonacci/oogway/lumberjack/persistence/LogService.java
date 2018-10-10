package guru.bonacci.oogway.lumberjack.persistence;

import static java.time.Instant.now;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class LogService {

	@Autowired
	private LogRepository repository;

	public Mono<Long> insert(Log logLine) {
		logLine.setMoment(now());
		repository.save(logLine);
		
		return repository.countByApiKeyAndMomentBetween(logLine.getApiKey(), 
														 now().minusSeconds(60), 
														 now());
	}
}
