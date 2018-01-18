package guru.bonacci.oogway.jobs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import guru.bonacci.oogway.oracle.client.OracleClientConfig;

@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableScheduling
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@Import(OracleClientConfig.class)
@PropertySource("classpath:twitter.properties")
public class JobRunner {

    public static void main(String[] args) {
		System.setProperty("spring.config.name", "job-runner");
        SpringApplication.run(JobRunner.class, args);
    }
}
