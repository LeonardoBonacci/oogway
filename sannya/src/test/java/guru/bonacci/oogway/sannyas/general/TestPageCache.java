package guru.bonacci.oogway.sannyas.general;

import org.springframework.stereotype.Component;

@Component
public class TestPageCache implements PageCache {

	int counter = 0;
	
	@Override
	public Integer getNrOfPages(String searchURL) {
		return ++counter;
	}

}
