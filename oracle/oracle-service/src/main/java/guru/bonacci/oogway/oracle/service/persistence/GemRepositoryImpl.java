package guru.bonacci.oogway.oracle.service.persistence;

import static guru.bonacci.oogway.utils.MyListUtils.getRandom;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import guru.bonacci.oogway.oracle.service.intercept.WatchMe;

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
	public void saveTheNewOnly(Gem... entities) {
		// until spring data's @CreatedDate works on elasticsearch we're doomed
		// to perform some manual labor
		LocalDateTime now = LocalDateTime.now();

		List<Gem> newOnes = stream(entities)
									  .filter(gem -> !gemRepository.exists(gem.getId()))
									  .peek(gem -> gem.setCreation(now))
									  .peek(gem -> logger.info("About to gain wisdom: '" + gem.getSaying() + "'"))
									  .peek(gem -> gem.setAuthor("TODO my by"))
									  .collect(toList());
		// strangely enough spring data or elasticsearch cannot deal with empty iterables
		if (!newOnes.isEmpty())
			gemRepository.save(newOnes);
	}

	@WatchMe // as spring data offers no proper hook to intercept search queries we do it the traditional way...
	@Override
	public Optional<Gem> consultTheOracle(String searchString, Optional<String> author) {
		SearchQuery searchQuery = createQuery(searchString, author);
		List<Gem> result = gemRepository.search(searchQuery).getContent();

		if (logger.isDebugEnabled())
			result.stream().map(Gem::getSaying).forEach(logger::debug);

		return getRandom(result);
	}
	
	private SearchQuery createQuery(String searchString, Optional<String> authorOpt) {
		NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder().withQuery(matchQuery(Gem.SAID, searchString));
		authorOpt.ifPresent(author -> queryBuilder.withFilter(termQuery(Gem.AUTHOR, author)));
		return queryBuilder.build();
	}
}
