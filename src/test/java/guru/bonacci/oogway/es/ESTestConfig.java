package guru.bonacci.oogway.es;

import java.net.InetAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories
public class ESTestConfig {

	@Bean
	public Client client() throws Exception {
		Settings esSettings = Settings.settingsBuilder().put("cluster.name", "bla").build();
		return TransportClient.builder().settings(esSettings).build()
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 1111));
	}

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() throws Exception {
		return new ElasticsearchTemplate(client());
	}

}
