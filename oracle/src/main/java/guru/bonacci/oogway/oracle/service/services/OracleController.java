package guru.bonacci.oogway.oracle.service.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OracleController {

	@GetMapping("/version")
	public String version(@Value("${build.version}") String buildVersion) {
		return buildVersion;
	}	
}
