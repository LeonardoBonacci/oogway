package guru.bonacci.oogway.web;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import guru.bonacci.oogway.web.events.WebEventChannels;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class WebIntegrationTest {

	@Autowired
	MockMvc mvc;

	@Autowired
    WebEventChannels channels;
	
	@Autowired
	MessageCollector messageCollector;

	@SuppressWarnings("unchecked")
	@Test
	public void shouldSendCOMINTMessageAfterInterception() throws Exception {
		String localIP = "127.0.0.1";
		String input = "The art of living is more like wrestling than dancing.";
		mvc.perform(get("/consult?q=" + input));
		
		Message<String> received = (Message<String>) messageCollector.forChannel(channels.spectreChannel()).poll();
		assertThat(received.getPayload(), equalTo("{\"ip\":\"" + localIP + "\",\"message\":\"" + input + "\"}"));
	}
}