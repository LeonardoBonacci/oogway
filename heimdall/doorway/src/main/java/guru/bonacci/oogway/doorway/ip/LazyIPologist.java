package guru.bonacci.oogway.doorway.ip;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class LazyIPologist implements IIPologist {

	@Override
	public String checkUp(String ip) {
		return ip;
	}
}
