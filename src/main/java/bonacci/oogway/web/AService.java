package bonacci.oogway.web;

import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import bonacci.oogway.oracle.Juwel;
import bonacci.oogway.sannyas.AManager;

@Service
public class AService {
	
	private final ARepository repository;

	private final AManager manager;

	@Autowired
	public AService(ARepository repository, AManager manager) {
		this.repository = repository;
		this.manager = manager;
	}

    public String index(String q) {
        if (StringUtils.isEmpty(q))
        	return "No question no answer..";

        //TODO camel message here...
    	manager.listen(q);
    	
    	SearchQuery searchQuery = new NativeSearchQueryBuilder()
    			  .withQuery(QueryBuilders.matchQuery(Juwel.ESSENCE, q))
    			  .build();
    	List<Juwel> result = repository.search(searchQuery).getContent();
    	return result.get(new Random().nextInt(result.size())).getEssence();
    }

    @PostConstruct
    private void fill() {
    	repository.deleteAll();
    }
}
