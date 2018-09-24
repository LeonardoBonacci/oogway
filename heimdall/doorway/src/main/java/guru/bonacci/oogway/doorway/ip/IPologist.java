package guru.bonacci.oogway.doorway.ip;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Iterator;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!prod")
public class IPologist implements IIPologist {

	private final Logger logger = getLogger(this.getClass());

	private Iterator<String> iperator;

	@Autowired
	public IPologist(IPerable iperable) {
		iperator = iperable.iterator();
	}

	@Override
	public String checkUp(String ipIn) {
		String ipOut = iperator.next();
		logger.debug(ipIn + " becomes " + ipOut);
		return ipOut;
	}
}
