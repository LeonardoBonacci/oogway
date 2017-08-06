package guru.bonacci.oogway.sannya.service.processing;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.sannya.service.gr.GRSeeker;
import guru.bonacci.oogway.shareddomain.GemDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
public class PitchforkManagerTest {

	@Autowired
	PitchforkManager manager;

	@MockBean
	SannyasinPicker sannyasinPicker;

	@MockBean
	GRSeeker sannyasin;

	@MockBean
	ForePlayer forePlayer;

	@MockBean
	CleaningAgent cleaningAgent;

//TODO	@MockBean
//	OracleMessageProducer messageProducer;

	@Test
	public void shouldJustRunThroughAllTheseMockCallsInThisNonMeaningfulTest() {
		String input = "yet another beautiful day today";
		String preprocessedInput = "another beautiful day";
		List<GemDTO> found = asList(new GemDTO("that"), new GemDTO("is true"), new GemDTO("beautiful stranger"));
		List<GemDTO> clutterless = asList(new GemDTO("that"), new GemDTO("true"), new GemDTO("stranger"));
		
		when(sannyasinPicker.pickOne()).thenReturn(sannyasin);
		when(forePlayer.play(sannyasin, input)).thenReturn(preprocessedInput);
		when(sannyasin.seek(preprocessedInput)).thenReturn(found);
		when(cleaningAgent.noMoreClutter(sannyasin, found)).thenReturn(clutterless);
		
		manager.delegate(input);
		//		verify(messageProducer, times(1)).save(clutterless);
	}
}
