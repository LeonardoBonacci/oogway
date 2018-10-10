package guru.bonacci.oogway.doorway.spectre;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnMissingBean(ToSpectre.class)
public class OrNotToSpectre implements Spectre {

	private final Logger logger = getLogger(this.getClass());

	@Override
	public void eavesdrop(String q, String ip) {
		logger.info("day off..");
	}
}
