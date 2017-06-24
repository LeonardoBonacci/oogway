package guru.bonacci.oogway.oracle.broadcast;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.oracle.GemRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
public class MessengerTest {

	@Autowired
	GemRepository repo;

	@Autowired
	Messenger messenger;

	@MockBean
	JmsTemplate jms;


	@Test
	public void shouldInterceptTheConsultMethod() {
		String searchString = "something completely different";
		repo.consultTheOracle(searchString);

		verify(jms, times(1)).send(anyString(), any(MessageCreator.class));
	}
}
