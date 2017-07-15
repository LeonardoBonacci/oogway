package guru.bonacci.oogway.spectre.geo.service.ip;

import static java.util.Arrays.asList;
import static org.slf4j.LoggerFactory.getLogger;
import static guru.bonacci.oogway.utils.MyListUtils.getRandom;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class IPologist implements IIPologist {

	private final Logger logger = getLogger(this.getClass());

	static final String LOCAL_IP_1 = "0:0:0:0:0:0:0:1";
	static final String LOCAL_IP_2 = "127.0.0.1";
	
	private List<String> randomIPs;

	@PostConstruct
	private void fill() {
		randomIPs = asList(
		    "119.168.33.192");
	}	

	@Override
	public String checkUp(String ipIn) {
		String ipOut = LOCAL_IP_1.equals(ipIn) || LOCAL_IP_2.equals(ipIn) ? getRandom(randomIPs).get() : ipIn;
		logger.debug(ipIn + " becomes " + ipOut);
		return ipOut;
	}
}
