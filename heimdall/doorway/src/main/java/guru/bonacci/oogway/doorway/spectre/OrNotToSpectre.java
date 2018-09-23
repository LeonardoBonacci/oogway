package guru.bonacci.oogway.doorway.spectre;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean(ToSpectre.class)
public class OrNotToSpectre implements Spectre {

	@Override
	public void eavesdrop(String q) {
		// day off..
	}
}
