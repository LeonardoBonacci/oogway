package guru.bonacci.oogway.oracle.persistence;

import static guru.bonacci.oogway.utils.MyListUtils.getRandom;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import guru.bonacci.oogway.oracle.broadcast.WatchMe;

/**
 * Following the spring data naming convention we implement 'custom
 * functionality' in a class called ...RepositoryImpl
 */
public class GemRepositoryImpl implements GemRepositoryCustom {

	private final Logger logger = getLogger(this.getClass());

	@Lazy // resolves circular dependency
	@Autowired
	private GemRepository gemRepository;

	/**
	 * ElasticSearch is not a writer. Like most of us, it reads better than it
	 * writes. A simple repo.save() will perform an unnecessary update when the
	 * document already exists. Therefore, this slight cumbersome workaround for
	 * when numbers get large.
	 */
	@Override
	public void saveTheNewOnly(PersistedGem... entities) {
		List<PersistedGem> newOnes = stream(entities)
									  .filter(gem -> !gemRepository.exists(gem.getId()))
									  .peek(gem -> logger.info("About to index wisdom: '" + gem + "'"))
									  .collect(toList());
		// strangely enough spring data or elasticsearch cannot deal with empty iterables
		if (!newOnes.isEmpty())
			gemRepository.save(newOnes);
	}

	@WatchMe // as spring data offers no proper hook to intercept search queries we do it the traditional way...
	@Override
	public Optional<PersistedGem> consultTheOracle(String searchString) {
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchQuery(PersistedGem.ESSENCE, searchString))
				.build();
		List<PersistedGem> result = gemRepository.search(searchQuery).getContent();

		if (logger.isDebugEnabled())
			result.stream().map(PersistedGem::getEssence).forEach(logger::debug);

		return getRandom(result);
	}
}
