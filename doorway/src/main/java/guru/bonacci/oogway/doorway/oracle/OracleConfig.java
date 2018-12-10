package guru.bonacci.oogway.doorway.oracle;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import guru.bonacci.oogway.doorway.cheaters.Postponer;
import guru.bonacci.oogway.doorway.clients.OracleClient;
import guru.bonacci.oogway.doorway.security.UserDetailsMagic;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class OracleConfig {

	@Value("${service.oracle.enabled:}")
	private boolean enabled;

	private final OracleClient client;

	private final UserDetailsMagic userDetails;

	private final Postponer postponer;
	
	
	@Bean
    public Function<String, Oracle> oracleFactory() {
        return (apikey) -> toOracle(apikey);
    } 
	
	// alternative to @Conditional's
	@Scope(value = SCOPE_PROTOTYPE)
	@Bean
    public Oracle toOracle(String apikey) {
		return enabled ? new ToOracle(client, userDetails, postponer, apikey) : new OrNotToOracle();
    }
}

