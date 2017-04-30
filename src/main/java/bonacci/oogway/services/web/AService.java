package bonacci.oogway.services.web;

import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import bonacci.oogway.es.Juwel;
import bonacci.oogway.jms.MessageSender;

@Service
public class AService {
	
	private final ARepository repository;

	private final MessageSender messageSender;

	@Autowired
	public AService(ARepository repository, MessageSender messageSender) {
		this.repository = repository;
		this.messageSender = messageSender;
	}
	
    public String index(String q) {
        if (StringUtils.isEmpty(q))
        	return "No question no answer..";

        // Send a message to the world...
        messageSender.sendMessage(q);

        // Consult the oracle..
    	SearchQuery searchQuery = new NativeSearchQueryBuilder()
    			  .withQuery(QueryBuilders.matchQuery(Juwel.ESSENCE, q))
    			  .build();
    	List<Juwel> result = repository.search(searchQuery).getContent();

    	return result.size() > 0 ? result.get(new Random().nextInt(result.size())).getEssence() 
    							 : "I'm speechless, are you sure?";
    }

    @PostConstruct
    private void fill() {
    	repository.deleteAll();
    }
}
