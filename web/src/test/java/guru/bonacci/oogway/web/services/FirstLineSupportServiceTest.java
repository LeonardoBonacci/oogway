package guru.bonacci.oogway.web.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.commons.Gem;
import guru.bonacci.oogway.web.helpers.Postponer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
public class FirstLineSupportServiceTest {

	@Autowired
	FirstLineSupportService service;

	@MockBean
	WebOracleService oracleService;

	@MockBean
	Postponer postponer;

	@Test
	public void shouldGiveEmptyStringAnswer() {
		assertThat(service.enquire(""), is(equalTo("No question no answer..")));
	}

	@Test
	public void shouldGiveAnswer() {
		Gem expected = new Gem("some answer");
		when(oracleService.consult(anyString())).thenReturn(Optional.of(expected));

		assertThat(service.enquire("some input"), is(equalTo(expected.getEssence())));
	}

	@Test
	public void shouldGivePostponingAnswer() {
		String postponingAnswer = "wait a second..";
		when(oracleService.consult(anyString())).thenReturn(Optional.empty());
		when(postponer.saySomething()).thenReturn(postponingAnswer);

		assertThat(service.enquire("some input"), is(equalTo(postponingAnswer)));
	}

}
