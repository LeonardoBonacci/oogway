package guru.bonacci.oogway.sannyas.filters;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class LengthFilterTest {

    @InjectMocks
    private LengthFilter lengthFilter;

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
