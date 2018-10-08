package guru.bonacci.oogway.jobs.services;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.jobs.twitter.Tweeter;

@RestController
public class JobController {

	private final Logger logger = getLogger(this.getClass());

    @Autowired
    public Tweeter tweeter;
    
	@GetMapping("/tweet")
	public String tweet() {
		logger.info("Manual tweet");
		
		tweeter.runForrestRun();
		return "tweet tweet";
	}	
}