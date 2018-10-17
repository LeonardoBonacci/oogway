package guru.bonacci.oogway.sannyas.processing;

import static guru.bonacci.oogway.utilities.CustomListUtils.random;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannyas.general.Sannyasin;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * urbandictionary.com 
 * pick - adj: to comb your afro 
 * Brotha pick yo' fro cause its flat on one side.
 * by cipher August 06, 2003
 */
@Component
@RequiredArgsConstructor
public class SannyasinPicker {

	private final ApplicationContext applicationContext;

	public Mono<Sannyasin> pickOne() {
		List<Sannyasin> sannyas = new ArrayList<>(applicationContext.getBeansOfType(Sannyasin.class).values());
		return Mono.fromSupplier(() -> random(sannyas).get());
	}

}
