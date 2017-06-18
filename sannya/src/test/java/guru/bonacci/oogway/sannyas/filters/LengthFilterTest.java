package guru.bonacci.oogway.sannyas.filters;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import guru.bonacci.oogway.oracle.GemRepository;
import guru.bonacci.oogway.sannyas.SannyasTestConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SannyasTestConfig.class)
public class LengthFilterTest {

    @Autowired
    LengthFilter lengthFilter;

	@MockBean //TODO remove
	GemRepository gemRepo;

   	private final static String LEO_SAID = "Study without desire spoils the memory, and it retains nothing that it takes in";

    @Test
    public void shouldBeLongerThan10Characters() {
	   	ReflectionTestUtils.setField(lengthFilter, "maxLength", 10);
	   	assertThat(lengthFilter.test(LEO_SAID), is(false));
    }

    @Test
    public void shouldNotBeLongerThan100Characters() {
	   	ReflectionTestUtils.setField(lengthFilter, "maxLength", 100);
	   	assertThat(lengthFilter.test(LEO_SAID), is(true));
    }

}
