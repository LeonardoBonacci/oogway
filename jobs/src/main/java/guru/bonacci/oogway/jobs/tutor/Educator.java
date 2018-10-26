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
			  .doOnNext(s -> log.info(s))
			  .subscribe(r -> oracleClient.search(r).subscribe());
	}
}