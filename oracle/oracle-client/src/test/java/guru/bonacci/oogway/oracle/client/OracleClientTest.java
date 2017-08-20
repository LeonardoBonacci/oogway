package guru.bonacci.oogway.oracle.client;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.oogway.shareddomain.GemCarrier;
import guru.bonacci.oogway.shareddomain.IGem;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=OracleClientTestApp.class, webEnvironment = NONE, properties = {
	"oracle.service.application.name:not-used"
})
public class OracleClientTest {

	@Autowired
	OracleClient client;
	
	@LoadBalanced
	@MockBean
	RestTemplate rest;

	@Test
	public void shouldAnswerQ() {
		String question = "how much do you charge per hour?";
		String answer = "it's free";
		
		doReturn(new GemCarrier(answer)).when(rest).getForObject("http://not-used" + "/gems?q={searchString}", GemCarrier.class, question, null);

		Optional<IGem> gem = client.consult(question);
		assertThat(gem.get().getSaying(), is(equalTo(answer)));
	}
	
	@Test
	public void shouldAnswerQAndBy() {
		String question = "how much do you charge per hour?";
		String by = "whom";
		String answer = "it's free";

		doReturn(new GemCarrier(answer)).when(rest).getForObject("http://not-used" + "/gems?q={searchString}&by={by}", GemCarrier.class, question, by);

		Optional<IGem> gem = client.consult(question, by);
		assertThat(gem.get().getSaying(), is(equalTo(answer)));
	}
}
