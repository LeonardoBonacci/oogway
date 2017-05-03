package bonacci.oogway.services.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AController {
    
	private final AService service;

	@Autowired
	public AController(AService service) {
		this.service = service;
	}
	
    @RequestMapping("/")
    public String index(@RequestParam("q") String q) {
    	return service.index(q);
    }
}
