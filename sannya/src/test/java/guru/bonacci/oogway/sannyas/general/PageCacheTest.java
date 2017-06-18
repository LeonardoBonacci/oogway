package guru.bonacci.oogway.sannyas.general;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.sannyas.SannyasTestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SannyasTestApplication.class)
public class PageCacheTest {

	@Autowired 
	TestPageCache pageCache;

	@Ignore //TODO
	@Test
	public void shouldCache() {
		Integer first = pageCache.getNrOfPages("first call");
		assertThat(first, is(equalTo(1)));
		Integer second = pageCache.getNrOfPages("second call");
		assertThat(second, is(equalTo(2)));
		Integer firstAgain = pageCache.getNrOfPages("first call");
		assertThat(firstAgain, is(equalTo(1)));
		Integer secondAgain = pageCache.getNrOfPages("second call");
		assertThat(secondAgain, is(equalTo(2)));
		Integer third = pageCache.getNrOfPages("third call");
		assertThat(third, is(equalTo(3)));
	}
}
