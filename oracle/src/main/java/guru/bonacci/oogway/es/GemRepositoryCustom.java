package guru.bonacci.oogway.es;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public interface GemRepositoryCustom {
	
	void saveTheNewOnly(Collection<Gem> gems);
	
	Optional<Gem> searchForOne(String searchString);
}
