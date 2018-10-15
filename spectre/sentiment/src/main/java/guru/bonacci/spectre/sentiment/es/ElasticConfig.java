package guru.bonacci.spectre.sentiment.es;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class ElasticConfig {

    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return objectMapper;
    }

    @Bean
    public RestHighLevelClient restHighLevelClient(ElasticsearchProperties props) {
        return new RestHighLevelClient(
                RestClient
                        .builder(props.hosts())
                        .setRequestConfigCallback(config -> config
                                .setConnectTimeout(props.getConnectTimeout())
                                .setConnectionRequestTimeout(props.getConnectionRequestTimeout())
                                .setSocketTimeout(props.getSocketTimeout())
                        )
                        .setMaxRetryTimeoutMillis(props.getMaxRetryTimeoutMillis()));
    }
}
