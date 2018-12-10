package guru.bonacci.oogway.doorway.clients;

import java.net.URLEncoder;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.hystrix.exception.HystrixRuntimeException;

import guru.bonacci.oogway.domain.GemCarrier;
import guru.bonacci.oogway.doorway.security.Credentials;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactivefeign.ReactiveOptions;
import reactivefeign.client.ReactiveHttpResponse;
import reactivefeign.client.statushandler.ReactiveStatusHandler;
import reactivefeign.cloud.CloudReactiveFeign;
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
				.options(new ReactiveOptions.Builder()
		                .setConnectTimeoutMillis(10000)
		                .setReadTimeoutMillis(10000)
		                .build())
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
	}
}
