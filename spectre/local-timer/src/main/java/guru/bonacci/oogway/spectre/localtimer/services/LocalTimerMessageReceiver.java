package guru.bonacci.oogway.spectre.localtimer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

@Component
public class LocalTimerMessageReceiver {

	@Autowired
	SpecRepository repo;

	@Autowired
	ElasticsearchTemplate template;

    @StreamListener(LocalTimerSink.ENRICHMENT)
	public void onMessage(Wrapper input) {
		System.out.println("localtimer " + input.getUuid());

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
