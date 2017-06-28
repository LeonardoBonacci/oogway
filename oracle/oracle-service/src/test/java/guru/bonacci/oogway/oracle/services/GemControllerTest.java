package guru.bonacci.oogway.oracle.services;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import guru.bonacci.oogway.oracle.persistence.Gem;
import guru.bonacci.oogway.oracle.persistence.GemRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GemControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	GemRepository gemRepo;
	
	@Test
	public void shouldReturnNullOnConsult() throws Exception {
		given(gemRepo.consultTheOracle("tell me the truth")).willReturn(Optional.empty());

		mvc.perform(get("/gems?q=tell me the truth"))
			.andExpect(status().isOk())
			.andExpect(content().string(isEmptyOrNullString()));
	}

	@Test
	public void shouldRecieveMessageOnConsult() throws Exception {
		given(gemRepo.consultTheOracle("tell me the truth")).willReturn(Optional.of(new Gem("why should I?")));

		mvc.perform(get("/gems?q=tell me the truth"))
			.andExpect(status().isOk())
			.andExpect(content().json("{'essence':'why should I?'}")); 
	}

	@Test
	public void shouldReceive200OnBackdoor() throws Exception {
		mvc.perform(post("/backdoor").content("tell me the truth"))
			.andExpect(status().isOk());
	}
}
