package guru.bonacci.oogway.oracle.prep;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;

@Configuration
public class MetricsConfig {

	@Bean
	@ConditionalOnProperty(value = "spring.metrics.binders.jvmthreads.enabled", matchIfMissing = true)
	@ConditionalOnMissingBean(JvmThreadMetrics.class)
	public JvmThreadMetrics jvmThreadMetrics() {
		return new JvmThreadMetrics();
	}

	@Bean
	@ConditionalOnProperty(value = "spring.metrics.binders.jvmgc.enabled", matchIfMissing = true)
	@ConditionalOnMissingBean(JvmGcMetrics.class)
	public JvmGcMetrics jvmGcMetrics() {
		return new JvmGcMetrics();
	}
}
