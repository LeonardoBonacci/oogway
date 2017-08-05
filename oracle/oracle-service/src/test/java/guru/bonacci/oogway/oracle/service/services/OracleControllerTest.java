package guru.bonacci.oogway.oracle.service.services;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import guru.bonacci.oogway.oracle.service.persistence.Gem;
import guru.bonacci.oogway.oracle.service.persistence.GemRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OracleControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	GemRepository gemRepo;
	
	@Test
	public void shouldReturnNullOnConsult() throws Exception {
		given(gemRepo.consultTheOracle("tell me the truth", null)).willReturn(Optional.empty());

		mvc.perform(get("/gems?q=tell me the truth"))
			.andExpect(status().isOk())
			.andExpect(content().string(isEmptyOrNullString()));
	}

	@Test
	public void shouldRecieveMessageOnQ() throws Exception {
		given(gemRepo.consultTheOracle("tell me the truth", Optional.of("dummy")))
			.willReturn(Optional.of(new Gem("why should I?", "dumb")));

		mvc.perform(get("/gems?q=tell me the truth&by=dummy"))
			.andExpect(status().isOk())
			.andExpect(content().json("{'saying':'why should I?', 'author':'dumb'}")); 
	}
	
	@Test
	public void shouldRecieveMessageOnQAndBy() throws Exception {
		given(gemRepo.consultTheOracle("tell me the truth", Optional.of("dummy")))
			.willReturn(Optional.of(new Gem("why should I?", "dumb")));

		mvc.perform(get("/gems?q=tell me the truth&by=dummy"))
			.andExpect(status().isOk())
			.andExpect(content().json("{'saying':'why should I?', 'author':'dumb'}")); 
	}

}
