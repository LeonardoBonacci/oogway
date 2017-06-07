package guru.bonacci.oogway.sannyas;

import static org.slf4j.LoggerFactory.getLogger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import guru.bonacci.oogway.jms.SmokeSignal;

@Controller
public class SmokeSignalController implements MessageListener {

	private final Logger logger = getLogger(this.getClass());

	@Autowired
	private MessageConverter messageConverter;

	@Autowired
	private PitchforkManager manager;

	@Override
	public void onMessage(Message message) {
		try {
			String input = ((SmokeSignal) messageConverter.fromMessage(message)).getMessage();
			logger.info("Received message <" + input + ">");
			manager.delegate(input);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping(path = "/backdoor", method = RequestMethod.POST)
	public void index(@RequestBody String input) {
		logger.info("receiving backdoor index request for: '" + input + "'");
		manager.delegate(input);
	}
}
