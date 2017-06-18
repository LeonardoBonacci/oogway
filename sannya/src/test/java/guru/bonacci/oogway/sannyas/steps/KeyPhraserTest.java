package guru.bonacci.oogway.sannyas.steps;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class KeyPhraserTest {

	private String input;
	private String output;

    public KeyPhraserTest(String in, String out) {
        this.input = in;
        this.output = out;
    }

     @Parameters
     public static Collection<Object[]> data() {
         return asList(new Object[][]{
	         {"If I have seen further it is by standing on the shoulders of Giants.", "have seen standing shoulders of Giants"},
	         {"I can calculate the motion of heavenly bodies but not the madness of people.", "calculate motion of heavenly bodies madness of people"},
	         {"Tact is the knack of making a point without making an enemy.", "Tact knack making point making enemy"},
	         {"Nature is pleased with simplicity. And nature is no dummy" , "Nature pleased simplicity nature dummy"},
	         {"Hello, My Name Is Doris" , "Name Doris"}
         });
     }
     
     @Test
     public void shouldDeduceKeyPhrases() {
         assertThat(new KeyPhraser().apply(input), is(equalTo(output)));
     }
}
