package guru.bonacci.oogway.sannyas.general;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.sannyas.general.PageTestCaches.PageTestCache1;
import guru.bonacci.oogway.sannyas.general.PageTestCaches.PageTestCache2;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SannyasCachedTestApplication.class, webEnvironment = NONE)
public class PageCacheTest {

	@Autowired 
	PageTestCache1 pageCache;

	@Autowired 
	PageTestCache2 pageCache2;

	@Test
	public void shouldCacheOnTwoBeansWithSameNamedCache() {
		int nr = pageCache.getNrOfPages("first call");
		assertThat(nr, is(equalTo(1)));

		nr = pageCache.getNrOfPages("first call");
		assertThat(nr, is(equalTo(1)));

		nr = pageCache.getNrOfPages("second call");
		assertThat(nr, is(equalTo(2)));

		nr = pageCache2.getNrOfPages("first call");
		assertThat(nr, is(equalTo(1))); //first call is cached by the other bean
	}
}
