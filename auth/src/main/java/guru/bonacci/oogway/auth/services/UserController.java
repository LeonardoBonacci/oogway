package guru.bonacci.oogway.auth.services;

import static org.slf4j.LoggerFactory.getLogger;

import java.security.Principal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import guru.bonacci.oogway.auth.models.User;
import guru.bonacci.oogway.auth.security.RSAPasswordEncoderTests;

@RestController
@RequestMapping("/users")
public class UserController {

	private final Logger logger = getLogger(this.getClass());

	@Autowired 
	private MyUserService userService;

	@Autowired 
	private RSAPasswordEncoderTests passwordEncoder;

	@RequestMapping(value = "/current", method = RequestMethod.GET)
    public Principal user(Principal user) {
        logger.info(user.getName() + " is under investigation");
        logger.debug("user info: " + user.toString());
        return user;
    }
	
	@PreAuthorize("#oauth2.hasScope('resource-server-read')")
	@RequestMapping(value = "/user1", method = RequestMethod.GET)
	public User getUserInfo() {
		User u = userService.loadUserByApiKey("1resu");
		u.setPw(passwordEncoder.decode(u.getPassword()));
		return u;
	}
}
