package guru.bonacci.oogway.sannya.service.steps;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.function.Function;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;

import guru.bonacci.oogway.sannya.service.SannyasTestApp;

/**
 * 
 * Subclass defines test data by implementing
 * @Parameters
 * public static Collection<Object[]> data() {}
 */
@RunWith(value = Parameterized.class)
@ContextConfiguration(classes = SannyasTestApp.class)
public abstract class AbstractStepTest<F extends Function<String,String>> {

	@Autowired
	ApplicationContext appContext;

	Class<F> typeParameterClass;
	F step;

	
	// Manually config for spring to use Parameterised
	private TestContextManager testContextManager;

	private String input;
	private String output;

	public AbstractStepTest(String in, String out, Class<F> typeParameterClass) {
		this.typeParameterClass = typeParameterClass;
		this.input = in;
		this.output = out;
	}

	//if @TestPropertySource("proxy.enabled=false") would work on a contextConfiguration 
	//this hack would not be needed
	@BeforeClass
	public static void beforeClass() {
        System.setProperty("proxy.enabled", "false");
    }

	@AfterClass
	public static void afterClass() {
	    System.clearProperty("some.property");
	}
	
	@Before
	public void setUp() throws Exception {
		this.testContextManager = new TestContextManager(getClass());
		this.testContextManager.prepareTestInstance(this);

		// not the nicest way, but well..
		step = appContext.getBean(typeParameterClass);
	}

	@Test
	public void shouldWork() {
		assertThat(step.apply(input), is(equalTo(output)));
	}
}
