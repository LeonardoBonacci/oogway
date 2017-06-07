package guru.bonacci.oogway.sannyas.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class DuplicateRemoverTest {

     @Test
     public void shouldRemoveDuplicates() {
    	 String withDuplicates = "hello hello I am going home hello home";
    	 String withoutDuplicates = "hello I am going home";
    	 assertThat(new DuplicateRemover().apply(withDuplicates), is(equalTo(withoutDuplicates)));
     }
}
