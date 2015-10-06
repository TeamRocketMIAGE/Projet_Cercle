package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

public class SecurityController {

	 private final InMemoryUserDetailsManager inMemoryUserDetailsManager;

	    @Autowired
	    public SecurityController(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
	       this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
	    }

	    @RequestMapping("exists/{username}")
	    public boolean userExists(@PathVariable("username") String username ) {
	        return inMemoryUserDetailsManager.userExists(username);
	    }

	    
	    /*
	    @RequestMapping("add/{username}/{password}")
	    public String add(@PathVariable("username") String username, @PathVariable("password") String password) {
	        inMemoryUserDetailsManager.createUser(new User(username, password));
	        return "added";
	    }
	    */
	    
	
}
