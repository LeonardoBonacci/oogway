package guru.bonacci.oogway.spectre.geo.service.ip;

import static java.util.Arrays.asList;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.utils.MyListUtils;

@Component
@Profile("dev")
public class IPologist implements IIPologist {

	private final Logger logger = getLogger(this.getClass());

	static final String LOCAL_IP = "0:0:0:0:0:0:0:1";

	private List<String> randomIPs;

	@PostConstruct
	private void fill() {
		randomIPs = asList(
		    "b44a:d399:1a82:fdd4:336d:8a4f:6251:2219",
		    "98c4:7deb:f8e9:9a95:2b32:6355:327:a63f",
		    "adff:f46e:c9e2:92a3:468d:acf8:3a22:8375",
		    "c8c2:8dee:490c:713f:1f83:9825:d0df:aadd",
		    "77ad:20ac:dbbd:7807:1653:d139:ce92:c335",
		    "a12c:9477:d7f0:302c:9fc7:cd12:8ba5:ca89",
		    "3643:bacd:bb36:ff8d:b5c6:9c2b:451:2783",
		    "471:f885:4888:6d5d:aed6:6612:51e9:a510",
		    "e8ff:6e94:b098:5d47:fc47:fcc:31b6:b6b9",
		    "7cb8:b:85d1:1d3e:44af:73db:c454:9bd7",
		    "a967:1eb1:4439:14ba:56c4:f5d6:fd71:c96b",
		    "5367:e613:cea:7102:81e3:37ac:6569:439f");
	}	

	@Override
	public String checkUp(String ipIn) {
		String ipOut = LOCAL_IP.equals(ipIn) ? MyListUtils.getRandom(randomIPs).get() : ipIn;
		logger.debug(ipIn + " becomes " + ipOut);
		return ipOut;
	}
}
