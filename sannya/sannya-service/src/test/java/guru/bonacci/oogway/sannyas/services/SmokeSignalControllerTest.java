package guru.bonacci.oogway.sannyas.services;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SmokeSignalControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	PitchforkManager manager;
	
	@Test
	public void shouldReceive200OnBackdoor() throws Exception {
		mvc.perform(post("/backdoor").content("tell me the truth"))
			.andExpect(status().isOk());
	}
}
