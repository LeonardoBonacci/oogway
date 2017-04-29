package bonacci.oogway.web;

import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import bonacci.oogway.jms.SmokeSignal;
import bonacci.oogway.oracle.Juwel;

@Service
public class AService {
	
	private final ARepository repository;
	private final JmsTemplate jmsTemplate;

	@Autowired
	public AService(ARepository repository, JmsTemplate jmsTemplate) {
		this.repository = repository;
		this.jmsTemplate = jmsTemplate;
	}

    public String index(String q) {
        if (StringUtils.isEmpty(q))
        	return "No question no answer..";

        // Send a message - the template reuse the message converter
        jmsTemplate.convertAndSend("winnetou", new SmokeSignal(q));

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
