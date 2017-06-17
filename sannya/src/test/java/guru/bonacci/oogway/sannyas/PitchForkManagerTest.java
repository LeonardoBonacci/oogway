package guru.bonacci.oogway.sannyas;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.oracle.GemRepository;
import guru.bonacci.oogway.sannyas.services.CleaningAgent;
import guru.bonacci.oogway.sannyas.services.ForePlayer;
import guru.bonacci.oogway.sannyas.services.PitchforkManager;
import guru.bonacci.oogway.sannyas.services.SannyasPicker;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SannyasTestConfig.class)
public class PitchforkManagerTest {

	@Autowired
	PitchforkManager manager;

	@Mock
	SannyasPicker sannyasPicker;

	@Mock
	ForePlayer forePlayer;

	@Mock
	CleaningAgent cleaningAgent;

	@Mock
	GemRepository repository;

	@Test
	public void shouldGiveEmptyStringAnswer() {
	}


}
