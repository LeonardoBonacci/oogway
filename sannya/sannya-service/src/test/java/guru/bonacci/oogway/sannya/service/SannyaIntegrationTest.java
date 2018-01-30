package guru.bonacci.oogway.sannya.service;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.messaging.MessageHeaders.CONTENT_TYPE;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.sannya.service.events.SannyaEventChannels;
import guru.bonacci.oogway.sannya.service.gr.GRSeeker;
import guru.bonacci.oogway.sannya.service.processing.SannyasinPicker;
import guru.bonacci.oogway.shareddomain.GemCarrier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {"proxy.enabled=false"})
public class SannyaIntegrationTest {

	@Autowired
	BinderAwareChannelResolver resolver;

	@MockBean
	SannyasinPicker picker;

	@MockBean
	GRSeeker sannyasin;

	@Autowired
    SannyaEventChannels channels;
	
	@Autowired
	MessageCollector messageCollector;

	@SuppressWarnings("unchecked")
	@Test
	public void shouldDoSomething() throws Exception {
		GemCarrier hit = new GemCarrier("does not matter", "dear");

		// DON'T SEEK!
		doReturn(sannyasin).when(picker).pickOne();
		doReturn(asList(hit)).when(sannyasin).seek(anyString());

		//send..
		String body = "{\"content\":\"I am Malcolm X\"}";
		sendMessage(body, SannyaEventChannels.ORACLE,"application/json");

		//..and, after processing, receive published event
		Message<GemCarrier> received = (Message<GemCarrier>) messageCollector.forChannel(channels.sannyaChannel()).poll();
		assertThat(received.getPayload(), equalTo(hit));
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
	@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
											value = {SannyasServer.class}))
	@EnableBinding(SannyaEventChannels.class)
	@IntegrationComponentScan
	static class SannyaIntegrationTestApp {

		static void main(String[] args) {
			SpringApplication.run(SannyaIntegrationTestApp.class, args);
		}
	}
}