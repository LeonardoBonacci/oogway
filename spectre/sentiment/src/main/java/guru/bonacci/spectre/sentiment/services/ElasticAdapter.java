package guru.bonacci.spectre.sentiment.services;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Component;

import guru.bonacci.spectre.sentiment.es.Spec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

@SuppressWarnings("deprecation")
@Component
@Slf4j
@RequiredArgsConstructor
public class ElasticAdapter {

	public static final String INDEX = "logstash-spectre";
	public static final String TYPE = "logs";
	
    private final RestHighLevelClient client;

    Mono<Spec> findById(String id) {
    	return Mono
                .<GetResponse>create(sink ->
                        client.getAsync(new GetRequest(INDEX, TYPE, id), listenerToSink(sink))
                )
                .filter(GetResponse::isExists)
                .map(GetResponse::getSource)
                .map(map -> Spec.builder().message(map.get(Spec.MESSAGE).toString()).id(id).build());
    }

    Mono<UpdateResponse> update(String specId, String field, Object nestedObject) {
        return updateDoc(specId, field, nestedObject);
    }

    private Mono<UpdateResponse> updateDoc(String docId, String field, Object nestedObject) {
        return Mono.create(sink -> {
            try {
                doUpdate(docId, field, nestedObject, listenerToSink(sink));
            } catch (IOException e) {
        	   sink.error(e);
			}
        });
    }

    private void doUpdate(String docId, String field, Object nestedObject, ActionListener<UpdateResponse> listener) throws IOException {
    	final UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, docId);
        updateRequest.doc(jsonBuilder().startObject().field(field, nestedObject).endObject());
        client.updateAsync(updateRequest, RequestOptions.DEFAULT, listener);
    }
    
    private <T> ActionListener<T> listenerToSink(MonoSink<T> sink) {
        return new ActionListener<T>() {
            @Override
            public void onResponse(T response) {
                sink.success(response);
            }

            @Override
            public void onFailure(Exception e) {
            	log.error(e.getMessage());
                sink.error(e);
            }
        };
    }

}
