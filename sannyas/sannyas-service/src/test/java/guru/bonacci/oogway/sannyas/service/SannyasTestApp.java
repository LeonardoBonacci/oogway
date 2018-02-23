package guru.bonacci.oogway.sannyas.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;

import guru.bonacci.oogway.sannyas.service.SannyasServer;

import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
										value = {SannyasServer.class}))
public class SannyasTestApp {

	public static void main(String[] args) {
		SpringApplication.run(SannyasTestApp.class, args);
	}
}