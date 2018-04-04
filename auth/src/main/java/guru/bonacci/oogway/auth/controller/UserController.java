package guru.bonacci.oogway.auth.controller;

import static org.slf4j.LoggerFactory.getLogger;

import java.security.Principal;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	private final Logger logger = getLogger(this.getClass());

    @RequestMapping
    public Principal user(Principal user) {
        logger.info("AS /user has been called");
        logger.debug("user info: " + user.toString());
        return user;
    }
}
