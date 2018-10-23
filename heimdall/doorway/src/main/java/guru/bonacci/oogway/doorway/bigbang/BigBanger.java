package guru.bonacci.oogway.doorway.bigbang;

import static java.time.Duration.between;
import static java.time.LocalDateTime.now;
import static java.time.Month.OCTOBER;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class BigBanger {

	private static final LocalDateTime BIG_BANG = LocalDateTime.of(2018, OCTOBER, 2, 22, 22);;


	public Mono<String> everSince() {
		return Mono.fromSupplier(() -> prettyPrint(between(BIG_BANG, now()).getSeconds()));
	}
	
	public static String prettyPrint(long seconds) {
	    long sec = seconds % 60;
	    long minutes = seconds % 3600 / 60;
	    long hours = seconds % 86400 / 3600;
	    long days = seconds / 86400;

	    return days + " days " + hours + " hours " + minutes + " minutes " + sec + " seconds";
	}
}
