package guru.bonacci.oogway.spectre.geo.service.ip;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;
import static guru.bonacci.oogway.spectre.geo.service.ip.IPologist.LOCAL_IP_1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.bonacci.oogway.spectre.geo.service.ip.IPologist;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE)
public class IPologistTest {

    @Autowired
    IPologist ipologist;

    @Test
    public void shouldReturnRandomIpWhenLocal() {
    	assertThat(ipologist.checkUp(LOCAL_IP_1), is(not(equalTo(LOCAL_IP_1))));
    }

    @Test
    public void shouldReturnSameIpWhenNotLocal() {
    	String ip = "164.243.120.46";
    	assertThat(ipologist.checkUp(ip), is(equalTo(ip)));
    }
}
