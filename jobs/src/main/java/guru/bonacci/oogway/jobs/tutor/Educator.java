package guru.bonacci.oogway.jobs.tutor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.jobs.clients.OracleClient;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class Educator {

	private final OracleClient oracleClient;


	@Scheduled(cron = "${tutor.cron}")
	public void educate() {
		log.info("Teaching");

		Mono<GemCarrier> random = oracleClient.random();
		random.map(r -> r.getSaying())
			  .subscribe(r -> {
				  log.info("random quote " + r.toString());
				  oracleClient.search(r).subscribe(f -> log.info("to find " + f.toString()));
			  });
	}
}