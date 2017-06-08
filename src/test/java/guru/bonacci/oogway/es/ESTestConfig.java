package guru.bonacci.oogway.es;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories
public class ESTestConfig {

	@Bean
	public Client client() {
		Node node = NodeBuilder.nodeBuilder().local(true)
	    .settings(Settings.builder()
	        .put("path.home", "C:/Tools/elasticsearch-2.4.4"))
	    .node();
//		Node node = NodeBuilder.nodeBuilder().local(true).node();
		return node.client();
	}	

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() {
		return new ElasticsearchTemplate(client());
	}

}
