package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class inscriptionController {
	
	//@Autowired
	//User user;
	
	
	@RequestMapping(value = "/inscription", method = RequestMethod.POST)
	public String requestInscription() {

		
		return "index";

	}
	


}
