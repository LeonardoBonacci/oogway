package guru.bonacci.oogway.sannya.service.processing;

import static guru.bonacci.oogway.utils.MyListUtils.getRandom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannya.service.general.Sannyasin;

/**
 * urbandictionary.com 
 * pick - adj: to comb your afro 
 * Brotha pick yo' fro cause its flat on one side.
 * by cipher August 06, 2003
 */
@Component
public class SannyasPicker {

	@Autowired
	private ApplicationContext applicationContext;

	public Sannyasin pickOne() {
		List<Sannyasin> sannyas = new ArrayList<>(applicationContext.getBeansOfType(Sannyasin.class).values());
		return getRandom(sannyas).get();
	}
}