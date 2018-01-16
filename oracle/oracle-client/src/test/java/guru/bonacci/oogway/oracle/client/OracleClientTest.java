package guru.bonacci.oogway.oracle.client;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import guru.bonacci.oogway.shareddomain.GemCarrier;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=OracleClientTestApp.class, webEnvironment=NONE, properties = {
		"oracle.service.application.name:not-used"
})
public class OracleClientTest {

    MockRestServiceServer server;

    @Autowired
    RestTemplate rest;

    @Autowired
    OracleClient client;

	@Autowired
	ObjectMapper objectMapper;

    @Before
    public void setup() {
        this.server = MockRestServiceServer.createServer(rest);
    }

    @After
    public void teardown() {
        this.server = null;
    }

    @Test
	public void shouldAnswerQ() throws JsonProcessingException {
    	GemCarrier gem = new GemCarrier("bla");
        this.server.expect(requestTo("http://not-used/gems?q=something"))
			        .andExpect(method(HttpMethod.GET))
			        .andRespond(withSuccess(objectMapper.writeValueAsString(gem), MediaType.APPLICATION_JSON));
        
		assertThat(client.consult("something", null).get()).isEqualTo(gem);
    }

    @Test
    public void shouldAnswerQAndBy() throws JsonProcessingException {
    	GemCarrier gem = new GemCarrier("bla", "bloe");
        this.server.expect(requestTo("http://not-used/gems?q=something&by=someone"))
                .andExpect(method(HttpMethod.GET))
		        .andRespond(withSuccess(objectMapper.writeValueAsString(gem), MediaType.APPLICATION_JSON));
        
        assertThat(client.consult("something", "someone").get()).isEqualTo(gem);
    }

    @Test
    public void shouldFallbackConsult() {
    	this.server.expect(requestTo("http://not-used/gems?q=something&by=someone"))
        		.andExpect(method(HttpMethod.GET))
        		.andRespond(withServerError());
        
        Optional<GemCarrier> gem = client.consult("something", "someone");
        assertThat(gem.isPresent(), is(false));
    }

    @Test
    public void shouldFindRandom() throws JsonProcessingException {
    	GemCarrier gem = new GemCarrier("bla", "bloe");
        this.server.expect(requestTo("http://not-used/gems/random"))
                .andExpect(method(HttpMethod.GET))
		        .andRespond(withSuccess(objectMapper.writeValueAsString(gem), MediaType.APPLICATION_JSON));
        
        assertThat(client.random().get()).isEqualTo(gem);
    }

    @Test
    public void shouldFallbackRandom() {
    	this.server.expect(requestTo("http://not-used/gems/random"))
        		.andExpect(method(HttpMethod.GET))
        		.andRespond(withServerError());
        
        Optional<GemCarrier> gem = client.random();
        assertThat(gem.isPresent(), is(false));
    }
    
}
