package guru.bonacci.oogway.spectre.geo.service.ip;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!dev")
public class LazyIPologist implements IIPologist {

	@Override
	public String checkUp(String ip) {
		return ip;
	}
}
