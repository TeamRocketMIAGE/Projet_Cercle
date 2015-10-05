package main;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class mainController {
	
	@RequestMapping("/home")
    public String requestCreatePageIndex() {
    	
    	return "index";
    	
    }

	@RequestMapping("/telechargement")
    public String requestCreatePageTelechargement() {
    	
    	return "telechargement";
    	
    }
	
	
	@RequestMapping("/inscription")
    public String requestCreatePageInscription() {
    	
    	return "inscription";
    	
    }
}
