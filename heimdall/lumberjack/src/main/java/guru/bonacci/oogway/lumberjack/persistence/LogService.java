package guru.bonacci.oogway.lumberjack.persistence;

import static java.time.Instant.now;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class LogService {

	@Autowired
	private LogRepository repository;

	@PreAuthorize("hasRole('read')")
	public Mono<Long> insert(Log logLine) {
		log.info("check on " + logLine.getApiKey());

		logLine.setMoment(now());
		return repository.save(logLine).flatMap(l -> repository.countByApiKeyAndMomentBetween(logLine.getApiKey(), 
														 now().minusSeconds(60), 
														 now()));
	}
}
