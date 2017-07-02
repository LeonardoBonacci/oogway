package guru.bonacci.oogway.sannyas.steps;

import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.runners.Parameterized.Parameters;

public class CharacterGuardianTest extends AbstractStepTest<CharacterGuardian>{

	public CharacterGuardianTest(String in, String out) {
		super(in, out, CharacterGuardian.class);
	}

    @Parameters
    public static Collection<Object[]> data() {
        return asList(new Object[][]{
         {"bla bla", "bla bla"},
         {"!hi &^&^there &", "hi there "},
         {"ab 123c.", "ab 123c"},
         {" 123 " , " 123 "},
         {"" , ""}
        });
    }
}
