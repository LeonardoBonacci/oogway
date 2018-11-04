package guru.bonacci.oogway.relastic;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.elasticsearch.common.xcontent.XContentType.JSON;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.ingest.PutPipelineRequest;
import org.elasticsearch.action.ingest.WritePipelineResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.bytes.BytesArray;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

@RequiredArgsConstructor
@Component
public class ElasticAdminAdapter {

	private final RestHighLevelClient client;

	
	public Mono<WritePipelineResponse> putPipeline(String id, String source) {
		PutPipelineRequest request = new PutPipelineRequest(id, new BytesArray(source.getBytes(UTF_8)), JSON);
		request.timeout(TimeValue.timeValueMinutes(2));
		return Mono.create(sink -> doPutPipeline(request, listenerToSink(sink)));
	}

	private void doPutPipeline(PutPipelineRequest request, ActionListener<WritePipelineResponse> listener) {
		request.timeout(TimeValue.timeValueMinutes(2));
		client.ingest().putPipelineAsync(request, RequestOptions.DEFAULT, listener);
	}
	
	private <U> ActionListener<U> listenerToSink(MonoSink<U> sink) {
		return new MonoSinkListener<U>(sink);	
	}
}
