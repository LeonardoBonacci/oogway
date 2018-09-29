package guru.bonacci.oogway.oracle.sannyas;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean(ToSannyas.class)
public class OrNotToSannyas implements Sannyas {

	@Override
	public void learn(String q) {
		// not a thing...
	}
}
