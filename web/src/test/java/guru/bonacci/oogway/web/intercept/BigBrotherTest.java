package guru.bonacci.oogway.web.intercept;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.shareddomain.COMINT;
import guru.bonacci.oogway.web.events.SpectreGateway;
import guru.bonacci.oogway.web.services.FirstLineSupportService;
import guru.bonacci.oogway.web.utils.IPCatcher;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
//A little hack to avoid creating profiles at this moment :)
// The test resource property overrides some of the web.properties that is
// read by the default configuration WebConfig
@TestPropertySource("classpath:web-test.properties")
public class BigBrotherTest {

	@Autowired
	FirstLineSupportService service;

	@Autowired
	BigBrother bigBrother;

	@MockBean
	IPCatcher iPCatcher;

	@MockBean
	SpectreGateway gateway;

	@Test
	public void shouldInterceptTheConsultMethod() {
		String searchString = "something completely different";
		service.enquire(searchString);

		when(iPCatcher.getClientIp()).thenReturn("123");
		//TODO verify(gateway, times(1)).send(isA(COMINT.class));
	}
}
