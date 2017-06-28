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

import guru.bonacci.oogway.oracle.api.IGem;
import guru.bonacci.oogway.oracle.client.OracleRESTClient.RESTGem;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE, properties = {
	"oracle.service.application.name:not-used"
})
public class OracleRestClientTest {

	@Autowired
	OracleRESTClient client;
	
	@LoadBalanced
	@MockBean
	RestTemplate rest;

	@Test
	public void shouldAnswerConsult() {
		String question = "how much do you charge per hour?";
		String answer = "it's free";
		doReturn(new RESTGem(answer)).when(rest).getForObject("http://not-used" + "/gems?q={searchString}", RESTGem.class, question);

		Optional<IGem> gem = client.consult(question);
		assertThat(gem.get().getEssence(), is(equalTo(answer)));
	}
}
