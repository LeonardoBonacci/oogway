package guru.bonacci.oogway.relastic;

import org.elasticsearch.action.ActionListener;

import reactor.core.publisher.MonoSink;

class MonoSinkListener<U> implements ActionListener<U> {

	private MonoSink<U> sink;
	
	MonoSinkListener(MonoSink<U> sink) {
		this.sink = sink;
	}

	@Override
	public void onResponse(U response) {
		sink.success(response);
	}

	@Override
	public void onFailure(Exception e) {
		sink.error(e);
	}
}