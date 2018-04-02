package guru.bonacci.oogway.jobs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import guru.bonacci.oogway.jobs.clients.OracleClient;
import guru.bonacci.oogway.jobs.clients.OracleClientConfig;

@SpringBootApplication
@EnableFeignClients(basePackageClasses = OracleClient.class)
@EnableCircuitBreaker
@EnableOAuth2Client
@EnableScheduling
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@Import(OracleClientConfig.class)
public class JobRunner {

    public static void main(String[] args) {
        SpringApplication.run(JobRunner.class, args);
    }
}
