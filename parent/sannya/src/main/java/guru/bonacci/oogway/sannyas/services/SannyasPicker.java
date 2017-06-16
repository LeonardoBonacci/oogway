package guru.bonacci.oogway.sannyas.services;

import static guru.bonacci.oogway.util.MyListUtils.getRandom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannyas.general.Sannyasin;

@Component
public class SannyasPicker {

	@Autowired
	private ApplicationContext applicationContext;

	public Sannyasin pickOne() {
		List<Sannyasin> sannyas = new ArrayList<>(applicationContext.getBeansOfType(Sannyasin.class).values());
		return getRandom(sannyas).get();
	}
}
