package guru.bonacci.oogway.lumberjack.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {

	@Autowired
	private LogRepository visitRepo;
	
	
}
