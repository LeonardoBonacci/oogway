package guru.bonacci.oogway.profanity;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import io.confluent.kafka.serializers.KafkaAvroSerializer;

@Configuration
@EnableKafka
public class Config {

	@Value("${bootstrap.url}")
	String bootstrap;
	
	@Value("${registry.url}")
	String registry;

	@Bean
	public ProducerFactory<String, Order> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}
	
	@Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> properties = new HashMap<>();

		// Kafka Properties
		properties.put("bootstrap.servers", bootstrap);
		properties.put("acks", "all");
		properties.put("retries", "10");
		// Avro properties
		properties.put("key.serializer", StringSerializer.class.getName());
		properties.put("value.serializer", KafkaAvroSerializer.class.getName());
		properties.put("schema.registry.url", registry);
		return properties;
    }
	
	@Bean
    public KafkaTemplate<String, Order> kafkaTemplate() {
        return new KafkaTemplate<String, Order>(producerFactory());
    }
}
