package guru.bonacci.oogway.spectre.localtimer.services;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

import java.io.IOException;

import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class LocalTimerMessageReceiver {

	@Autowired
	SpecRepository repo;

	@Autowired
	ElasticsearchTemplate template;

	@JmsListener(destination = "first-topic")
	public void onMessage(String input) throws IOException {
		System.out.println(input);
		Spec s = repo.findOne(input);

		UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("logstash-spectre");
        updateRequest.type("logs");
        updateRequest.id(s.getId());
        updateRequest.doc(jsonBuilder().startObject().field("title", "new title").endObject());
        UpdateQuery updateQuery = new UpdateQueryBuilder().withId(s.getId()).withClass(Spec.class).withUpdateRequest(updateRequest).build();
        template.update(updateQuery);
		
		repo.save(s);
	}
}
