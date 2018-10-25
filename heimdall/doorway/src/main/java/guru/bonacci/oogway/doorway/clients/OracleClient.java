package guru.bonacci.oogway.doorway.clients;

import java.net.URLEncoder;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.hystrix.exception.HystrixRuntimeException;

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
				.setFallbackFactory(cause -> new FallbackOracleApi(cause)).statusHandler(new ReactiveStatusHandler() {
					@Override
					public boolean shouldHandle(int status) {
						return status == 404 || status == 401 || status == 403;
					}

					@Override
					public Mono<? extends Throwable> decode(String method, ReactiveHttpResponse resp) {
						Exception e;
						switch (resp.status()){
							case 404:
								e = new NotFoundException();
								break;
							case 401:
								e = new UnauthorizedException();
								break;
							case 403:
								e = new UnauthorizedException();
								break;
							default:
								e = new Exception("nothing?");
								break;
						}
						return Mono.error(e);
					}
				}).target(OracleApi.class, url);
		return oracle;
	}

	
	@SuppressWarnings("deprecation")
	public Mono<GemCarrier> searchOne(String q, Credentials creds) {
		return theOracle(creds).searchOne(URLEncoder.encode(q))
				.onErrorResume(HystrixRuntimeException.class, e -> Mono.error(e.getCause()));
	}

	public Mono<String> create(GemCarrier gem, Credentials creds) {
		return theOracle(creds).create(gem)
				.onErrorResume(HystrixRuntimeException.class, e -> Mono.error(e.getCause()));
	}

	public Mono<Void> update(GemIdCarrier gem, Credentials creds) {
		return theOracle(creds).update(gem)
				.onErrorResume(HystrixRuntimeException.class, e -> Mono.error(e.getCause()))
				.onErrorResume(NoSuchElementException.class, e -> Mono.empty()); 
		//FIXME java.util.NoSuchElementException: Observable emitted no items
	}

	public Mono<Void> delete(String id, Credentials creds) {
		return theOracle(creds).delete(id)
				.onErrorResume(HystrixRuntimeException.class, e -> Mono.error(e.getCause()))
				.onErrorResume(NoSuchElementException.class, e -> Mono.empty()); //FIXME 
	}

	public Mono<GemIdCarrier> findById(String id, Credentials creds) {
		return theOracle(creds).retrieve(id)
				.onErrorResume(HystrixRuntimeException.class, e -> Mono.error(e.getCause()));
	}

	public Flux<GemIdCarrier> all(Credentials creds) {
		return theOracle(creds).all()
				.onErrorResume(HystrixRuntimeException.class, e -> Mono.error(e.getCause()));
	}

	@SuppressWarnings("deprecation")
	public Flux<GemCarrier> search(String q, Credentials creds) {
		return theOracle(creds).search(URLEncoder.encode(q))
				.onErrorResume(HystrixRuntimeException.class, e -> Mono.error(e.getCause()));
	}

	private class FallbackOracleApi implements OracleApi {

		private final Throwable cause;

		FallbackOracleApi(Throwable t) {
			cause = t;
		}


		@Override
		public Mono<GemCarrier> searchOne(String q) {
			log.error(cause.toString());
			return Mono.error(cause);
		}

		@Override
		public Flux<GemCarrier> search(String q) {
			log.error(cause.toString());
			return Flux.error(cause);
		}

		@Override
		public Mono<GemIdCarrier> retrieve(String id) {
			log.error(cause.toString());
			return Mono.error(cause);
		}

		@Override
		public Mono<String> create(GemCarrier gem) {
			log.error(cause.toString());
			return Mono.error(cause);
		}

		@Override
		public Mono<Void> update(GemIdCarrier gem) {
			log.error(cause.toString());
			return Mono.error(cause);
		}

		@Override
		public Mono<Void> delete(String id) {
			log.error(cause.toString());
			return Mono.error(cause);
		}

		@Override
		public Flux<GemIdCarrier> all() {
			log.error(cause.toString());
			return Flux.error(cause);
		}
	}
}
