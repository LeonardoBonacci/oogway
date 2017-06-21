package guru.bonacci.oogway.sannyas.services;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.oracle.Gem;
import guru.bonacci.oogway.oracle.GemRepository;
import guru.bonacci.oogway.sannyas.goodreads.GoodReadsSeeker;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
public class PitchforkManagerTest {

	@Autowired
	PitchforkManager manager;

	@MockBean
	SannyasPicker sannyasPicker;

	@MockBean
	GoodReadsSeeker sannyasin;

	@MockBean
	ForePlayer forePlayer;

	@MockBean
	CleaningAgent cleaningAgent;

	@MockBean
	GemRepository gemRepo;

	@Test
	public void shouldJustRunThroughAllTheseMockCallsInThisNonMeaningfulTest() {
		String input = "yet another beautiful day today";
		String preprocessedInput = "another beautiful day";
		List<String> found = asList("that", "is true", "beautiful stranger");
		List<Gem> clutterless = asList(new Gem("that"), new Gem("true"), new Gem("stranger"));
		
		when(sannyasPicker.pickOne()).thenReturn(sannyasin);
		when(forePlayer.play(sannyasin, input)).thenReturn(preprocessedInput);
		when(sannyasin.seek(preprocessedInput)).thenReturn(found);
		when(cleaningAgent.noMoreClutter(sannyasin, found)).thenReturn(clutterless);
		
		manager.delegate(input);
		verify(gemRepo, times(1)).saveTheNewOnly(clutterless);
	}
}
