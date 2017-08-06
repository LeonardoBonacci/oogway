package guru.bonacci.oogway.spectre.localtimer.events;

import static guru.bonacci.oogway.spectre.localtimer.events.LocalTimerEventChannels.ENRICHMENT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import guru.bonacci.oogway.shareddomain.GenericEvent;
import guru.bonacci.oogway.spectre.localtimer.services.SpecRepository;

@Component
public class LocalTimerRabbitEar {

	@Autowired
	SpecRepository repo;

	@Autowired
	ElasticsearchTemplate template;

    @StreamListener(ENRICHMENT)
	public void onMessage(GenericEvent event) {
		System.out.println("localtimer " + event.getContent());

//		Spec s = repo.findOne(input);

		// UpdateRequest updateRequest = new UpdateRequest();
		// updateRequest.index("logstash-spectre");
		// updateRequest.type("logs");
		// updateRequest.id(s.getId());
		// updateRequest.doc(jsonBuilder().startObject().field("title", "new
		// title").endObject());
		// UpdateQuery updateQuery = new
		// UpdateQueryBuilder().withId(s.getId()).withClass(Spec.class).withUpdateRequest(updateRequest).build();
		// template.update(updateQuery);
		//
		// repo.save(s);
	}
}
