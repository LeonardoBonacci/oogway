package guru.bonacci.oogway.oracle.service;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import guru.bonacci.oogway.oracle.service.events.OracleEventChannels;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource("classpath:oracle-test.properties")
@AutoConfigureMockMvc
public class OracleIntegrationOutTest {

	@Autowired
	MockMvc mvc;

	@Autowired
    OracleEventChannels channels;
	
	@Autowired
	MessageCollector messageCollector;

	@SuppressWarnings("unchecked")
	@Test
	public void shouldSendMessageAfterInterception() throws Exception {
		String q = "The art of living is more like wrestling than dancing.";
		mvc.perform(get("/gems?q=" + q));
		
		Message<String> received = (Message<String>) messageCollector.forChannel(channels.oracleChannel()).poll();
		assertThat(received.getPayload(), equalTo("{\"content\":\"" + q + "\"}"));
	}
}