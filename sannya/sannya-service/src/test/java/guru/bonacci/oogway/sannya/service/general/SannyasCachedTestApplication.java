package guru.bonacci.oogway.sannya.service.general;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

import guru.bonacci.oogway.sannya.service.SannyasTestConfig;

@SpringBootApplication
@EnableCaching(proxyTargetClass=true)
@Import(SannyasTestConfig.class)
public class SannyasCachedTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SannyasCachedTestApplication.class, args);
	}
}