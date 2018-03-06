package guru.bonacci.oogway.heimdall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class HeimdallApp {

	public static void main(String[] args) {
	    SpringApplication.run(HeimdallApp.class, args);
	  }
}