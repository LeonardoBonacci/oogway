package guru.bonacci.oogway.sannyas;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import guru.bonacci.oogway.jms.SmokeSignal;

@Controller
public class SannyasController implements MessageListener {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MessageConverter messageConverter;

	@Autowired
	private MyManager manager;

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
