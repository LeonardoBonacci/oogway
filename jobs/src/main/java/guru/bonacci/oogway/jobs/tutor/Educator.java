package guru.bonacci.oogway.jobs.tutor;

import static org.slf4j.LoggerFactory.getLogger;

import java.net.URLEncoder;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.jobs.clients.OracleClient;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import reactor.core.publisher.Mono;

@Component
public class Educator {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private OracleClient oracleClient;


	@SuppressWarnings("deprecation")
	@Scheduled(cron = "${tutor.cron}")
	public void educate() {
		logger.info("Teaching");

		Mono<GemCarrier> random = oracleClient.random();
		random.map(r -> r.getSaying())
			  .subscribe(r -> oracleClient.search(URLEncoder.encode(r)).subscribe());
	}
}