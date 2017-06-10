package guru.bonacci.oogway.jms;

import static java.util.Arrays.asList;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

@Configuration
@ComponentScan(basePackages="bonacci.oogway.jms")
public class JMSConfig {

	private static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";
	
	public static final String QUEUE = "winnetou";
	
	@Bean
	public ConnectionFactory connectionFactory(){
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
		connectionFactory.setTrustedPackages(asList("guru.bonacci.oogway.jms"));
		return connectionFactory;
	}

	@Bean
	public ConnectionFactory cachingConnectionFactory(){
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setTargetConnectionFactory(connectionFactory());
		connectionFactory.setSessionCacheSize(10);
		return connectionFactory;
	}

	/*
	 * Used here for Sending Messages.
	 */
	@Bean 
	public JmsTemplate jmsTemplate(){
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connectionFactory());
		template.setDefaultDestinationName(QUEUE);
		return template;
	}
	
	@Bean 
	MessageConverter converter(){
		return new SimpleMessageConverter();
	}
}
