package guru.bonacci.oogway.web.ip;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Iterable supplying an infinite stream of ip addresses
 */
@Component
@Profile("dev")
public class IPerable implements Iterable<String> {

	@Autowired
	private IPCrawler crawler;

	private int bufferSize = 10;
	private List<String> ipList = new ArrayList<>();

	public IPerable() {
	}

	public IPerable(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public IPerable(Collection<String> newIPs) {
		this.ipList.addAll(newIPs);
	}

	public IPerable(List<String> newIPs, int bufferSize) {
		this(newIPs);
		this.bufferSize = bufferSize;
	}

	@Override
	public Iterator<String> iterator() {
		Iterator<String> ips = new Iterator<String>() {

			private int currentIndex = 0;

			@Override
			public boolean hasNext() {
				return true;
			}

			@Override
			public String next() {
				if (currentIndex >= ipList.size()) {
					try {
						ipList = crawler.crawl(bufferSize);
					} catch (IOException e) {
						e.printStackTrace();
						ipList = asList("119.168.33.192", 
										"23.125.246.164", 
										"78.76.19.148", 
										"218.110.128.1",
										"157.179.74.159", 
										"119.95.229.45", 
										"34.121.147.91", 
										"6.93.254.186", 
										"195.41.169.4",
										"175.113.80.151");
					}
					currentIndex = 0;
				}
				return ipList.get(currentIndex++);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
		return ips;
	}
}
