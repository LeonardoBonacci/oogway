package guru.bonacci.oogway.sannyas.steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DuplicateRemoverTest {

    @InjectMocks
    private DuplicateRemover duplicateRemover;

     @Test
     public void shouldRemoveDuplicates() {
    	 String withDuplicates = "hello hello I am going home hello home";
    	 String withoutDuplicates = "hello I am going home";
    	 assertThat(duplicateRemover.apply(withDuplicates), is(equalTo(withoutDuplicates)));
     }
}
