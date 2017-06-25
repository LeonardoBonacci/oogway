package guru.bonacci.oogway.oracle.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

/**
 * Following the spring data naming convention we define a 'custom interface'
 * called ...RepositoryCustom
 */
@Repository
public interface GemRepositoryCustom {
	
	void saveTheNewOnly(PersistedGem... persistedGems);
	
	Optional<PersistedGem> consultTheOracle(String searchString);
}
