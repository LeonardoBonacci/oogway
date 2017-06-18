package guru.bonacci.oogway.web.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.oracle.Gem;
import guru.bonacci.oogway.oracle.GemRepository;
import guru.bonacci.oogway.web.WebTestApplication;
import guru.bonacci.oogway.web.helpers.Postponer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebTestApplication.class)
public class FirstLineSupportServiceTest {

	@Autowired
	FirstLineSupportService service;

	@MockBean
	GemRepository gemRepo;

	@MockBean
	JmsTemplate jms;

	@MockBean
	Postponer postponer;

	@Test
	public void shouldGiveEmptyStringAnswer() {
		assertThat(service.enquire(""), is(equalTo("No question no answer..")));
	}

	@Test
	public void shouldGiveAnswer() {
		Gem expected = new Gem("some answer");
		Mockito.doNothing().when(jms).send(Mockito.anyString(), Mockito.any(MessageCreator.class));
		Mockito.when(gemRepo.consultTheOracle(Mockito.anyString())).thenReturn(Optional.of(expected));

		assertThat(service.enquire("some input"), is(equalTo(expected.getEssence())));
	}

	@Test
	public void shouldGivePostponingAnswer() {
		String postponingAnswer = "wait a second..";
		Mockito.doNothing().when(jms).send(Mockito.anyString(), Mockito.any(MessageCreator.class));
		Mockito.when(gemRepo.consultTheOracle(Mockito.anyString())).thenReturn(Optional.empty());
		Mockito.when(postponer.saySomething()).thenReturn(postponingAnswer);

		assertThat(service.enquire("some input"), is(equalTo(postponingAnswer)));
	}

}
