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
public class LemmatizorTest {

	private String input;
	private String output;

    public LemmatizorTest(String in, String out) {
        this.input = in;
        this.output = out;
    }

     @Parameters
     public static Collection<Object[]> data() {
         return asList(new Object[][]{
	         {"How could you be seeing into my eyes like open doors?", "how could you be see into my eye like open door ?"},
	         {"You led me down into my core where I've became so numb", "you lead I down into my core where I have become so numb"},
	         {"Without a soul my spirit's sleeping somewhere cold", "without a soul my spirit 's sleep somewhere cold"},
	         {"Until you find it there and led it back home","until you find it there and lead it back home"},
	         {"You woke me up inside", "you wake I up inside"},
	         {"Called my name and saved me from the dark", "call my name and save I from the dark"},
	         {"You have bidden my blood and it ran", "you have bid my blood and it run"},
	         {"Before I would become undone", "before I would become undo"},
	         {"You saved me from the nothing I've almost become", "you save I from the nothing I have almost become"},
	         {"You were bringing me to life", "you be bring I to life"},
	         {"Now that I knew what I'm without", "now that I know what I be without"},
	         {"You can've just left me", "you can have just leave I"},
	         {"You breathed into me and made me real", "you breathe into I and make I real"},
	         {"Frozen inside without your touch", "frozen inside without you touch"},
	         {"Without your love, darling", "without you love , darling"},
	         {"Only you are the life among the dead", "only you be the life among the dead"},
	         {"I've been living a lie, there's nothing inside", "I have be live a lie , there be nothing inside"},
	         {"You were bringing me to life", "you be bring I to life"}
         });
     }
     
     @Test
     public void shouldLemmatize() {
         assertThat(new Lemmatizator().apply(input), is(equalTo(output)));
     }
}
