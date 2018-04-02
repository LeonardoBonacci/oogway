package guru.bonacci.oogway.web.clients;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OracleRequestFacilitator {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private RefreshScope refreshScope;
	
	@Autowired
	private ConfigurableEnvironment env;

	@PostConstruct
	public void init() {
		Map<String, Object> s = new HashMap<>();
		s.put("username", "user1");
		s.put("pw", "password");

		env.getPropertySources().addFirst(new MapPropertySource("security", s));
		refreshScope.refresh("client");
	}

	int i = 0;

	@Before("@annotation(OracleRequestInterceptor)")
	public void spreadTheNews(JoinPoint joinPoint) {
		//TODO this becomes the 'calling user'
		Map<String, Object> s = new HashMap<>();
		s.put("username", "user1");
		i++;
		if (i % 2 == 0)
			s.put("pw", "password");
		else 
			s.put("pw", "passwordsssss");

		logger.debug("Look who is calling: " + s.get("username") + " " + s.get("pw"));

		MutablePropertySources sources = env.getPropertySources();
		sources.replace("security", new MapPropertySource("security", s));
		
		refreshScope.refresh("client");
	}
}
