package guru.bonacci.oogway.spectre.sentiment;


import static java.util.Collections.singletonMap;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.messaging.MessageHeaders.CONTENT_TYPE;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.spectre.sentiment.events.SentimentEventChannels;
import guru.bonacci.oogway.spectre.sentiment.services.SentimentSpec;
import guru.bonacci.oogway.spectre.sentiment.services.SentimentSpecRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource("classpath:secret-persistence-test.properties")
public class SentimentIntegrationTest {

	@Autowired
	BinderAwareChannelResolver resolver;

	String uuid;

	@Autowired 
	SentimentSpecRepository repo;

	@Before
	public void init() {
		uuid = UUID.randomUUID().toString();
		
		SentimentSpec spec = new SentimentSpec();
		spec.id = uuid;
		spec.message = "what a wonderful wonderful life";
		repo.save(spec);
	}

	@After
	public void clean() {
		try {
			repo.delete(uuid);
		} catch (Exception ignore) {}
	}

	@Test
	public void shouldEventuallyAddData() {
		String body = "{\"content\":\"" + uuid + "\"}";
		sendMessage(body, SentimentEventChannels.ENRICHMENT,"application/json");

		SentimentSpec persisted = repo.findOne(uuid);
		assertThat(persisted.message, is(equalTo("what a wonderful wonderful life")));
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
	@EnableElasticsearchRepositories("guru.bonacci.oogway.spectre.sentiment.services")
	public static class TestApp {
		public static void main(String[] args) {
			SpringApplication.run(TestApp.class, args);
		}
	}
}