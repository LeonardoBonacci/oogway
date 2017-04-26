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

import bonacci.oogway.oracle.Article;
import bonacci.oogway.parser.AManager;

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
    			  .withQuery(QueryBuilders.matchQuery("title", q))
    			  .build();
    	List<Article> result = repository.search(searchQuery).getContent();
    	return result.get(new Random().nextInt(result.size())).getTitle();
    }

    @PostConstruct
    public void fill() {
    	repository.deleteAll();
    	//repo.findAll().forEach(a -> System.out.println(a.getId()));
    }

}
