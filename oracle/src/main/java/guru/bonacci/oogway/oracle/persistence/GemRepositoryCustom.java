package guru.bonacci.oogway.oracle.persistence;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import guru.bonacci.oogway.core.Gem;

/**
 * Following the spring data naming convention we define a 'custom interface'
 * called ...RepositoryCustom
 */
@Repository
public interface GemRepositoryCustom {
	
	void saveTheNewOnly(Collection<Gem> gems);
	
	Optional<Gem> consultTheOracle(String searchString);
}
