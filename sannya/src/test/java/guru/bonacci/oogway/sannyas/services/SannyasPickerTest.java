package guru.bonacci.oogway.sannyas.services;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.sannyas.SannyasTestApplication;
import guru.bonacci.oogway.sannyas.general.Sannyasin;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SannyasTestApplication.class)
public class SannyasPickerTest {

	@Autowired
	SannyasPicker picker;

	@Test
    public void shouldPickDifferentOnes() {
		Set<Sannyasin> result = new HashSet<>();	
		for (int i=0; i<10; i++)
			result.add(picker.pickOne());
		
		assertThat(result.size(), greaterThan(1));
	}
}
