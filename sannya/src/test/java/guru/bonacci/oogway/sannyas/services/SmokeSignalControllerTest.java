package guru.bonacci.oogway.sannyas.services;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import guru.bonacci.oogway.sannyas.SannyasTestConfig;
import guru.bonacci.oogway.sannyas.services.PitchforkManager;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = {SannyasTestConfig.class})
public class SmokeSignalControllerTest {

	@Autowired
	WebApplicationContext wac;

	MockMvc mvc;

	@MockBean
	PitchforkManager manager;
	
	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void shouldReceive200OnBackdoor() throws Exception {
		mvc.perform(post("/backdoor").content("tell me the truth"))
			.andExpect(status().isOk());
	}
}
