package main;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class mainController {
	
	@RequestMapping("/")
    public String requestCreatePage() {
    	
    	return "index";
    	
    }

	@RequestMapping("/telechargement")
    public String requestCreatePage1() {
    	
    	return "telechargement";
    	
    }
}
