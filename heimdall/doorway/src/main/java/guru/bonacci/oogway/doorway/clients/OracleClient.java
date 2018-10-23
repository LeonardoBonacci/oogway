package guru.bonacci.oogway.doorway.clients;

import java.net.URLEncoder;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import guru.bonacci.oogway.doorway.security.Credentials;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import guru.bonacci.oogway.shareddomain.GemIdCarrier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactivefeign.client.ReactiveHttpResponse;
import reactivefeign.client.statushandler.ReactiveStatusHandler;
import reactivefeign.cloud.CloudReactiveFeign;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class OracleClient {

	private final BiFunction<String, String, WebClient> webClientFactory;

	@Value("${service.oracle.url}") 
	private String url;

	
	private OracleApi theOracle(Credentials creds) {
		WebClient webClient = webClientFactory.apply(creds.getUsername(), creds.getPassword());
		OracleApi oracle = CloudReactiveFeign.<OracleApi>builder(webClient)
				.setFallbackFactory(cause -> new FallbackOracleApi(cause))
				.statusHandler(new ReactiveStatusHandler() {
					@Override
					public boolean shouldHandle(int status) {
						return status ==  404 || status ==  403;
					}
					
					@Override
					public Mono<? extends Throwable> decode(String method, ReactiveHttpResponse resp) {
						return Mono.error(new NotFoundException());
					}
				})
				.target(OracleApi.class, url);
		return oracle;
	}
	
	@SuppressWarnings("deprecation")
	public Mono<GemCarrier> searchOne(String q, Credentials creds) {
    	return theOracle(creds).searchOne(URLEncoder.encode(q))
    						   .onErrorResume(NoSuchElementException.class, t -> Mono.empty()); 
    }

	public Mono<String> create(GemCarrier gem, Credentials creds) {
    	return theOracle(creds).create(gem);
	}

	public Mono<Boolean> update(GemIdCarrier gem, Credentials creds) {
    	return theOracle(creds).update(gem)
    								 .onErrorResume(x -> Mono.empty()) // something strange here...
    								 .then(Mono.just(true));
	}

	public Mono<Boolean> delete(String id, Credentials creds) {
    	return theOracle(creds).delete(id)
									 .onErrorResume(x -> Mono.empty()) // something strange here...
									 .then(Mono.just(true));
	}

	public Mono<GemIdCarrier> findById(String id, Credentials creds) {
    	return theOracle(creds).retrieve(id)
    						   .onErrorResume(NoSuchElementException.class, t -> Mono.empty()); 
	}

	public Flux<GemCarrier> all(Credentials creds) {
    	return theOracle(creds).all();
	}

	@SuppressWarnings("deprecation")
	public Flux<GemCarrier> search(String q, Credentials creds) {
    	return theOracle(creds).search(URLEncoder.encode(q));
	}


	private class FallbackOracleApi implements OracleApi {

		private final Throwable cause;
	
		FallbackOracleApi (Throwable t) {
			cause = t;
		}

		private Mono<GemIdCarrier> notIdFounder(GemIdCarrier result) {
			return cause instanceof NotFoundException ? Mono.defer(() -> Mono.empty()) : Mono.fromSupplier(() -> result);
        }

		// To not break the contract a default should be sent. The downstream code assumes an empty mono to return an http 404. 
		// Therefore, working around for now, we nevertheless return an empty mono that should be caught by the caller as a NoSuchElementException.
		private Mono<GemCarrier> notFounder(GemCarrier result) {
			return Mono.defer(() -> cause instanceof NotFoundException ? Mono.empty() : Mono.just(result));
        }

		@Override
		public Mono<GemCarrier> searchOne(String q) {
			log.error(cause.toString());
			return notFounder(sorry());
        }

		@Override
		public Flux<GemCarrier> search(String q) {
			log.error(cause.toString());
            return Flux.defer(() -> Flux.just(sorry()));
        }

		@Override
		public Mono<GemIdCarrier> retrieve(String id) {
			log.error(cause.toString());
			return notIdFounder(sorryId());
		}

		@Override
		public Mono<String> create(GemCarrier gem) {
			log.error(cause.toString());
            return Mono.fromSupplier(() -> "Can't reach the Oracle");
		}

		@Override
		public Mono<Void> update(GemIdCarrier gem) {
			log.error(cause.toString());
            return Mono.defer(() -> Mono.empty());
		}

		@Override
		public Mono<Void> delete(String id) {
			log.error(cause.toString());
            return Mono.defer(() -> Mono.empty());
		}

		@Override
		public Flux<GemCarrier> all() {
			log.error(cause.toString());
            return Flux.defer(() -> Flux.just(sorry()));
		}

		private GemCarrier sorry() {
			return GemCarrier.builder().saying("Can't reach the Oracle").author("Sorry!").build();
		}

		private GemIdCarrier sorryId() {
			return new GemIdCarrier("Can't reach the Oracle");
		}
	}	
}

