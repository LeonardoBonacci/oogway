package guru.bonacci.oogway.util;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class MyFileUtilsTest {

	@Test
	public void shouldReadFileToList() throws IOException {
		List<String> lines = MyFileUtils.readToList("read-me-test.txt");
		assertThat(lines, hasSize(4));
	}
}
