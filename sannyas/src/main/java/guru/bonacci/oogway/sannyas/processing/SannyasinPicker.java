package guru.bonacci.oogway.sannyas.processing;

import static guru.bonacci.oogway.utilities.CustomListUtils.random;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannyas.general.Sannyasin;
import reactor.core.publisher.Mono;

/**
 * urbandictionary.com 
 * pick - adj: to comb your afro 
 * Brotha pick yo' fro cause its flat on one side.
 * by cipher August 06, 2003
 */
@Component
public class SannyasinPicker {

	@Autowired
	private ApplicationContext applicationContext;

	public Mono<Sannyasin> pickOne() {
		List<Sannyasin> sannyas = new ArrayList<>(applicationContext.getBeansOfType(Sannyasin.class).values());
		return Mono.just(random(sannyas).get());
	}

}
