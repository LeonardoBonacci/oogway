package guru.bonacci.oogway.monitoring.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class MonitoringServer {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringServer.class, args);
	}
}