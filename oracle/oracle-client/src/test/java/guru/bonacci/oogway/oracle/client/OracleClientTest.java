package guru.bonacci.oogway.oracle.client;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

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
    
    @Before
    public void setup() {
        this.server = MockRestServiceServer.createServer(rest);
    }

    @After
    public void teardown() {
        this.server = null;
    }

    @Test
	public void shouldAnswerQ() {
        this.server.expect(requestTo("http://not-used/gems?q=something"))
			        .andExpect(method(HttpMethod.GET))
			        .andRespond(withSuccess("{\"saying\":\"bla\"}", MediaType.APPLICATION_JSON));

		GemCarrier gem = client.consult("something", null).get();
		assertThat(gem).isEqualTo(new GemCarrier("bla"));
    }
    
    @Test
    public void shouldAnswerQAndBy() {
        this.server.expect(requestTo("http://not-used/gems?q=something&by=someone"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{\"saying\":\"bla\", \"author\":\"bloe\"}", MediaType.APPLICATION_JSON));
        
        GemCarrier gem = client.consult("something", "someone").get();
        assertThat(gem).isEqualTo(new GemCarrier("bla", "bloe"));
    }

    @Test
    public void shouldFallback() {
    	this.server.expect(requestTo("http://not-used/gems?q=something&by=someone"))
        		.andExpect(method(HttpMethod.GET))
        		.andRespond(withServerError());
        
        GemCarrier gem = client.consult("something", "someone").get();
        assertThat(gem).isEqualTo(new GemCarrier("Fall Back Hello world"));
    }

    
}
