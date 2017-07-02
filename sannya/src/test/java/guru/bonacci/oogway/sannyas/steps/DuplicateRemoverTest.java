package guru.bonacci.oogway.sannyas.steps;

import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

public class DuplicateRemoverTest extends AbstractStepTest<DuplicateRemover>{

	public DuplicateRemoverTest(String in, String out) {
		super(in, out, DuplicateRemover.class);
	}

    @Parameters
    public static Collection<Object[]> data() {
        return asList(new Object[][]{
         {"hello hello I am going home hello home", "hello I am going home"}
        });
    }
}
