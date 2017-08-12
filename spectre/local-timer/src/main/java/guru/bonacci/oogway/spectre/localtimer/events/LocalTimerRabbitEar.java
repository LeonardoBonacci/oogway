package guru.bonacci.oogway.spectre.localtimer.events;

import static guru.bonacci.oogway.spectre.localtimer.events.LocalTimerEventChannels.ENRICHMENT;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import guru.bonacci.oogway.shareddomain.GenericEvent;
import guru.bonacci.oogway.spectre.localtimer.services.GeoSpec;
import guru.bonacci.oogway.spectre.localtimer.services.GeoSpecRepository;
import guru.bonacci.oogway.spectre.localtimer.services.Spec;

@Component
public class LocalTimerRabbitEar {

	@Autowired
	GeoSpecRepository repo;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ElasticsearchTemplate template;

	public static final String INDEX = "logstash-spectre";
	public static final String TYPE = "logs";
	
	@StreamListener(ENRICHMENT)
	public void onMessage(GenericEvent event) {
		System.out.println("localtimer " + event.getContent());

		GeoSpec s = repo.findOne(event.getContent());

		String host = "http://api.geonames.org";
		String path = "/timezoneJSON";
		String user = "leonardobonacci";
		String searchQuery = "?lat=" + s.getGeoip().latitude + "&lng=" + s.getGeoip().longitude + "&username=" + user;
		
		try {
			@SuppressWarnings("unchecked")
			Map<String,Object> enrichment = restTemplate.getForObject(host + path + searchQuery, Map.class);
			System.out.println(enrichment);

			updater("localtimer3", enrichment, s);
		} catch(Exception e) {
			e.printStackTrace();
		}

		

//		Client client = NodeBuilder.nodeBuilder().client(true).node().client();
//		boolean indexExists = client.admin().indices().prepareExists(INDEX).execute().actionGet().isExists();
//		client.admin().indices().prepareCreate(INDEX).execute().actionGet();
//		SearchResponse allHits = client.prepareSearch(INDEX).addFields("title")
//				.setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
//		System.out.println(allHits.getHits().getTotalHits());

	}
	
	public void updater(String key, Object nestedObject, Spec s) throws IOException {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index(INDEX);
		updateRequest.type(TYPE);
		updateRequest.id(s.getId());
		updateRequest.doc(jsonBuilder().startObject().field(key, nestedObject).endObject());
		UpdateQuery updateQuery = new UpdateQueryBuilder().withId(s.getId())
														  .withClass(Spec.class)
														  .withUpdateRequest(updateRequest)
														  .build();
		template.update(updateQuery);
	}

}
