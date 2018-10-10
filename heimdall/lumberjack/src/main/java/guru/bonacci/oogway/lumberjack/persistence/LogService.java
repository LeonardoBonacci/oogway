package guru.bonacci.oogway.lumberjack.persistence;

import static java.time.Instant.now;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class LogService {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private LogRepository repository;

	public Mono<Long> insert(Log logLine) {
		logger.info("check on " + logLine.getApiKey());

		logLine.setMoment(now());
		return repository.save(logLine).flatMap(l -> repository.countByApiKeyAndMomentBetween(logLine.getApiKey(), 
														 now().minusSeconds(60), 
														 now()));
	}
}
