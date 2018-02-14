package guru.bonacci.oogway.oracle.service;

import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;

import guru.bonacci.oogway.oracle.service.events.OracleEventChannels;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Micro-service for the user to communicate with
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableElasticsearchRepositories
@EnableAspectJAutoProxy 
@IntegrationComponentScan
@EnableBinding(OracleEventChannels.class)
public class OracleServer {

	public static void main(String[] args) {
		SpringApplication.run(OracleServer.class, args);
	}
	
	@Bean
    public Docket gemApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(basePackage("guru.bonacci.oogway.oracle.service.services"))
                .paths(regex("/gems.*"))
                .build()
                .apiInfo(metaData());
    }
	
	private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Oracle Service REST API",
                "REST API for Oracle Service",
                "1.0",
                "Terms of service",
                new Contact("Leonardo Bonacci", "https://en.wikipedia.org/wiki/Fibonacci", "aabcehmu@mailfence.com"),
                "Eyn aruhath hinam :-)",
                "http://www.insidefortlauderdale.com/1500/Operating-without-a-License");
        return apiInfo;
    }
}
