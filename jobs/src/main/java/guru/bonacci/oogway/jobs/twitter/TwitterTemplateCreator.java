package guru.bonacci.oogway.jobs.twitter;

import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Component;

@Component
public class TwitterTemplateCreator {

	public Twitter getTwitterTemplate() {
		TwitterTemplate twitterTemplate = new TwitterTemplate(
				"shIN5k9zQWOLMGo769ANGGtKZ", 
				"mIQ0hgpKMMgXg3iP1AN7CpLMNQcGfoLuWdDHKOcaXcoSupFgFI", 
				"884771066496913409-YfIJwKPDo6DLhslhsqBUBCekXxtl8JG",
				"ALxVXquVaD6oY7Ptog2AFkpCCMv2LaacB2KEWwzLYTn1g");
		return twitterTemplate;
	}
}