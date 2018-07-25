package guru.bonacci.oogway.sannyas.service.filters;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import guru.bonacci.oogway.sannyas.service.SannyasTestApp;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SannyasTestApp.class, properties = {
        "spring.sleuth.enabled=false",
        "spring.zipkin.enabled=false"
}, webEnvironment = NONE)
public class LengthFilterTests {

   	private final static String LEO_SAID = "Study without desire spoils the memory, and it retains nothing that it takes in";

    @Autowired
    LengthFilter lengthFilter;

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
