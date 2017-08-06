package guru.bonacci.oogway.spectre.localtimer;


import static java.util.Collections.singletonMap;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.messaging.MessageHeaders.CONTENT_TYPE;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.spectre.localtimer.events.LocalTimerEventChannels;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class LocalTimerIntegrationTest {

	@Autowired
	BinderAwareChannelResolver resolver;

	@Test
	public void shouldDoSomething() throws Exception {
		String uuid = UUID.randomUUID().toString(); 
		
		String body = "{\"content\":\"" + uuid + "\"}";
		sendMessage(body, LocalTimerEventChannels.ENRICHMENT,"application/json");

		//TODO test something
	}

	private void sendMessage(String body, String target, Object contentType) {
		resolver.resolveDestination(target).send(MessageBuilder.createMessage(body,
				new MessageHeaders(singletonMap(CONTENT_TYPE, contentType))));
	}
	
	@Bean
	public MessageChannel routerChannel() {
		return new DirectChannel();
	}
	
	@SpringBootApplication
	public static class TestApp {

		public static void main(String[] args) {
			SpringApplication.run(TestApp.class, args);
		}
	}
}