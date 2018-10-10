package guru.bonacci.oogway.oracle.persistence;

import static guru.bonacci.oogway.utilities.CustomListUtils.random;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

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
	 * document already exists. Therefore, this slightly cumbersome workaround for
	 * when numbers get large.
	 */
	@Override
	public void saveTheNewOnly(Gem... entities) {
		// current version does not contain the bug fix for existsById
		// https://jira.spring.io/browse/DATAES-363			
		List<Gem> newOnes = stream(entities)
				.filter(gem -> !gemRepository.findById(gem.getId()).isPresent())
											 .peek(gem -> logger.info("About to gain wisdom: '" + gem.getSaying() + "'"))
				    						 .collect(toList());
		// strangely enough spring data or elasticsearch cannot deal with empty iterables
		if (!newOnes.isEmpty())
			gemRepository.saveAll(newOnes);
	}

	@Override
	public Optional<Gem> consultTheOracle(String searchString) {
		return consultTheOracle(searchString, null);
	}

	@Override
	public Optional<Gem> consultTheOracle(String searchString, String author) {
		SearchQuery searchQuery = createQuery(searchString, author);
		List<Gem> result = gemRepository.search(searchQuery).getContent();

		if (logger.isDebugEnabled())
			result.stream().map(Gem::getSaying).forEach(logger::debug);

		return random(result);
	}

	private SearchQuery createQuery(String searchString, String author) {
		NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder().withQuery(matchQuery(Gem.SAYING, searchString));
		if (author != null)
			queryBuilder.withFilter(termQuery(Gem.AUTHOR, author));
		return queryBuilder.build();
	}
}