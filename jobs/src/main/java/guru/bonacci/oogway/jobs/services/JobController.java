package guru.bonacci.oogway.jobs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.jobs.tutor.Educator;

@RestController
public class JobController {

    @Autowired
    public Educator educator;

    
	@GetMapping("/teach")
	public String teach() {
		educator.educate();
		return "Education is what remains after one has forgotten what one has learned in school.";
	}	
}