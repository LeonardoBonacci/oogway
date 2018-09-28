package guru.bonacci.oogway.sannyas.service.processing;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import guru.bonacci.oogway.sannyas.gr.GRSeeker;
import guru.bonacci.oogway.sannyas.processing.CleaningAgent;
import guru.bonacci.oogway.sannyas.processing.ForePlayer;
import guru.bonacci.oogway.sannyas.processing.SannyasinPicker;
import guru.bonacci.oogway.sannyas.services.SannyasService;
import guru.bonacci.oogway.shareddomain.GemCarrier;
import reactor.core.publisher.Flux;

@ExtendWith(SpringExtension.class)
public class PitchforkManagerTests {

	@TestConfiguration
    static class TestContext {
  
        @Bean
        public SannyasService manager() {
    		return new SannyasService();
        }
    }

	@Autowired
	SannyasService manager;

	@MockBean
	SannyasinPicker sannyasinPicker;

	@MockBean
	GRSeeker sannyasin;

	@MockBean
	ForePlayer forePlayer;

	@MockBean
	CleaningAgent cleaningAgent;

	@Test
	public void shouldJustRunThroughAllTheseMockCallsInThisMeaninglessTest() {
		String input = "yet another beautiful day today";
		String preprocessedInput = "another beautiful day";
		Flux<GemCarrier> found = Flux.just(new GemCarrier("that"), new GemCarrier("is true"), new GemCarrier("beautiful stranger"));
		List<GemCarrier> clutterless = asList(new GemCarrier("that"), new GemCarrier("true"), new GemCarrier("stranger"));
		
		when(sannyasinPicker.pickOne()).thenReturn(sannyasin);
		when(forePlayer.play(sannyasin, input)).thenReturn(preprocessedInput);
		when(sannyasin.seek(preprocessedInput)).thenReturn(found);
//TODO		when(cleaningAgent.noMoreClutter(sannyasin, found)).thenReturn(clutterless);
		
		manager.feed(input);
		//TODO verify something
	}
}
