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

import guru.bonacci.oogway.secretdomain.GemDTO;
import guru.bonacci.oogway.secretdomain.IGem;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE, properties = {
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
		doReturn(new GemDTO(answer)).when(rest).getForObject("http://not-used" + "/gems?q={searchString}", GemDTO.class, question);

		Optional<IGem> gem = client.consult(question, answer);
		assertThat(gem.get().getSaying(), is(equalTo(answer)));
	}
	
	@Test
	public void shouldAnswerQAndBy() {
		String question = "how much do you charge per hour?";
		String by = "whom";
		String answer = "it's free";
		doReturn(new GemDTO(answer)).when(rest).getForObject("http://not-used" + "/gems?q={searchString}&?by={by}", GemDTO.class, question, by);

		Optional<IGem> gem = client.consult(question, answer);
		assertThat(gem.get().getSaying(), is(equalTo(answer)));
	}
}
