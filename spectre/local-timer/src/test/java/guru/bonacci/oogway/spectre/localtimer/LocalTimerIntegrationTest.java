package guru.bonacci.oogway.spectre.localtimer;


import static java.util.Collections.singletonMap;
import static org.mockito.Mockito.doReturn;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.messaging.MessageHeaders.CONTENT_TYPE;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.oogway.spectre.localtimer.events.LocalTimerEventChannels;
import guru.bonacci.oogway.spectre.secretpersistence.Spec;
import guru.bonacci.oogway.spectre.secretpersistence.SpecRepository;
import guru.bonacci.oogway.spectre.secretpersistence.SpecRepositoryCustom;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {
	"geo.name.username=voldemort"		
})
//A little hack to avoid creating profiles at this moment :)
//The test resource property overrides some of the secret-persisence.properties that is
//read by the default configuration SecretPersistenceConfig
@TestPropertySource("classpath:secret-persistence-test.properties")
public class LocalTimerIntegrationTest {

	String uuid;
	
	@Autowired
	BinderAwareChannelResolver resolver;

	@Autowired 
	SpecRepository repo;

	@Autowired 
	TestSpecRepository testRepo;

	@MockBean
	RestTemplate rest;

	@Before
	public void init() {
		uuid = UUID.randomUUID().toString();
		
		Spec spec = new Spec();
		spec.id = uuid;
		spec.geoip = spec.new Geoip(1.1, 2.2);
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
		Map<String,Object> enrichmentData = new HashMap<>();
		enrichmentData.put("a", "is not b");
		doReturn(enrichmentData).when(rest).getForObject("http://api.geonames.org/timezoneJSON?lat=1.1&lng=2.2&username=voldemort", Map.class);
		
		String body = "{\"content\":\"" + uuid + "\"}";
		sendMessage(body, LocalTimerEventChannels.ENRICHMENT, "application/json");

		TestSpec persisted = testRepo.findOne(uuid);
		System.out.println(persisted);
	}

	private void sendMessage(String body, String target, Object contentType) {
		resolver.resolveDestination(target).send(MessageBuilder.createMessage(body,
				new MessageHeaders(singletonMap(CONTENT_TYPE, contentType))));
	}
	
	@Bean
	public MessageChannel routerChannel() {
		return new DirectChannel();
	}

	@Repository
	public interface TestSpecRepository extends ElasticsearchRepository<TestSpec, String> {
	}

	public class TestSpec extends Spec {
		
		@Field(type = FieldType.String)
		public String test;

	}
	
	@SpringBootApplication
	public static class TestApp {

		public static void main(String[] args) {
			SpringApplication.run(TestApp.class, args);
		}
	}
}