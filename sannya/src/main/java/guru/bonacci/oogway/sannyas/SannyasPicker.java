package guru.bonacci.oogway.sannyas;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.sannyas.general.Sannyasin;
import guru.bonacci.oogway.util.MyListUtils;

@Component
public class SannyasPicker {

	@Autowired
	private ApplicationContext applicationContext;

	public Sannyasin pickOne() {
		List<Sannyasin> sannyas = new ArrayList<>(applicationContext.getBeansOfType(Sannyasin.class).values());
		return MyListUtils.getRandom(sannyas).get();
	}
}
