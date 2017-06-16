package guru.bonacci.oogway.es;

import static guru.bonacci.oogway.util.MyListUtils.getRandom;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

public class GemRepositoryImpl implements GemRepositoryCustom {

	private final Logger logger = getLogger(this.getClass());

	@Lazy //resolves circular dependency
	@Autowired 
	private GemRepository gemRepository;

	/**
	 * ElasticSearch is not a writer. Like most of us, it reads better than it writes.
	 * A simple repo.save() will perform an unnecessary update when the document already exists.
	 * Therefore, this slight cumbersome workaround for when numbers get large. 
	 */
	@Override
	public void saveTheNewOnly(Collection<Gem> entities) {
		Stream<Gem> newOnes = entities.stream()
									  .filter(gem -> !gemRepository.exists(gem.getId()))
									  .peek(gem -> logger.info("About to index wisdom: '" + gem + "'"));
		Iterable<Gem> it = newOnes::iterator;
		gemRepository.save(it);
	}
	
	@Override
	public Optional<Gem> searchForOne(String searchString) {
		// Consult the oracle..
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
										.withQuery(matchQuery(Gem.ESSENCE, searchString)).build();
		List<Gem> result = gemRepository.search(searchQuery).getContent();
		
		if (logger.isDebugEnabled()) 
			result.stream().map(Gem::getEssence).forEach(logger::debug); 

		return getRandom(result);
	}
}
