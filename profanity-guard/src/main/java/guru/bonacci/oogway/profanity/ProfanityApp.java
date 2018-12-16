package guru.bonacci.oogway.profanity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableBinding(Binding.class)
@SpringBootApplication
public class ProfanityApp {

	public static void main(String[] args) {
		SpringApplication.run(ProfanityApp.class, args);
	}

	@RequestMapping("/orders")
	public String doIt(@RequestParam(value="name", defaultValue="Order-avro") String name)
	{
		
		String ret=name;
		try
		{
			ret += sendMsg(name);
		}
		catch(Exception ex){ ret+="<br>"+ex.getMessage();}
		
		return ret;
	}

	@Autowired
	private KafkaTemplate<String, Order> template;

	private Order sendMsg(String name)
	{
        Order order = Order.newBuilder()
        		.setOrderId("OId234")
        		.setCustomerId("CId432")
        		.setSupplierId("SId543")
                .setItems(4)
                .setFirstName(name)
                .setLastName("V")
                .setPrice(178f)
                .setWeight(75f)
                .build();

        template.send("bla", order);
        return order;
	}
}
