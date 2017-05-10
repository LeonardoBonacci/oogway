package guru.bonacci.oogway.services.web;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import guru.bonacci.oogway.es.Jewel;
import guru.bonacci.oogway.es.MyRepository;
import guru.bonacci.oogway.jms.SmokeSignal;
import guru.bonacci.oogway.util.RandomUtils;

@Service
public class MyService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MyRepository repository;

	@Autowired
	private JmsTemplate jmsTemplate;

    public String index(String q) {
		if (StringUtils.isEmpty(q))
        	return "No question no answer..";

        // Send a message to the world...
		jmsTemplate.send(session -> session.createObjectMessage(new SmokeSignal(q)));
        
        // Consult the oracle..
    	SearchQuery searchQuery = new NativeSearchQueryBuilder()
    			  .withQuery(QueryBuilders.matchQuery(Jewel.ESSENCE, q))
    			  .build();
    	List<Jewel> result = repository.search(searchQuery).getContent();
		result.stream().map(Jewel::getEssence).forEach(logger::debug);
    	
    	return result.size() > 0 ? result.get(RandomUtils.fromZeroExclTo(result.size())).getEssence() 
    							 : "I'm speechless, are you sure?";
    }
}
