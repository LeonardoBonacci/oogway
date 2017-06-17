package guru.bonacci.oogway.sannyas;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.oracle.GemRepository;
import guru.bonacci.oogway.sannyas.services.CleaningAgent;
import guru.bonacci.oogway.sannyas.services.ForePlayer;
import guru.bonacci.oogway.sannyas.services.PitchforkManager;
import guru.bonacci.oogway.sannyas.services.SannyasPicker;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SannyasTestConfig.class})
public class PitchForkManagerTest {

	@InjectMocks
	@Autowired
	PitchforkManager manager;

	@Mock
	private SannyasPicker sannyasPicker;

	@Mock
	private ForePlayer forePlayer;

	@Mock
	private CleaningAgent cleaningAgent;

	@Mock
	private GemRepository repository;

	@Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

	@Test
	public void shouldGiveEmptyStringAnswer() {
	}


}
