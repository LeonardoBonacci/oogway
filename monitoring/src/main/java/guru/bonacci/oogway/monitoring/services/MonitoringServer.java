package guru.bonacci.oogway.monitoring.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;

@SpringBootApplication
@EnableTurbineStream
@EnableHystrixDashboard
public class MonitoringServer {

	public static void main(String[] args) {
		SpringApplication.run(MonitoringServer.class, args);
	}
}