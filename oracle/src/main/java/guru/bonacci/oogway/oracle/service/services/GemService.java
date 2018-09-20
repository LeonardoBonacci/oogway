package guru.bonacci.oogway.oracle.service.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import guru.bonacci.oogway.oracle.service.persistence.Gem;
import guru.bonacci.oogway.oracle.service.persistence.GemRepository;

@Service
public class GemService {

	@Autowired
	private GemRepository repo;

	@PreAuthorize("#oauth2.hasScope('resource-server-read')")
	public Optional<Gem> search(String q, Optional<String> author) {
		return author.map(a -> repo.consultTheOracle(q, a))
					  .orElse(repo.consultTheOracle(q));
	}	

	@PreAuthorize("#oauth2.hasScope('resource-server-read')")
	public Optional<Gem> random() {
		return repo.findRandom(); 
	}	

	@PostMapping("/backdoor")
	public void index(Gem gem) {
		repo.saveTheNewOnly(gem);
	}
}
