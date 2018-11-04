package guru.bonacci.oogway.relastic;

import org.elasticsearch.action.ActionListener;

import reactor.core.publisher.FluxSink;

class FluxSinkListener<U> implements ActionListener<U> {

	private FluxSink<U> sink;
	
	FluxSinkListener(FluxSink<U> sink) {
		this.sink = sink;
	}

	@Override
	public void onResponse(U response) {
		// not the beautiful reactive stream that we were hoping for...
		sink.next(response);
		sink.complete();
	}

	@Override
	public void onFailure(Exception e) {
		sink.error(e);
	}
}