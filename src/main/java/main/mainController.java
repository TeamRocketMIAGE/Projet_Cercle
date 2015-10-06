package main;




import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class mainController {
	
	 @RequestMapping(value="/home", method=RequestMethod.GET)
    public String requestCreatePageIndex1() {
    	
		 System.out.println("request home");
    	return "index";
    	
    }
	
	@RequestMapping("/")
    public String requestCreatePageIndex2() {
    	
		return "redirect:/home";
    	
    }
	

	@RequestMapping("/telechargement")
    public String requestCreatePageTelechargement() {
    	
    	return "telechargement";
    	
    }
	
	
	@RequestMapping("/inscription")
    public String requestCreatePageInscription() {
    	
    	return "inscription";
    	
    }
	
	@RequestMapping("/user_page")
    public String requestCreatePageUserHome() {
    	
		System.out.println("user_page");
    	return "user_page";
    	
    }
	
	
	
	@RequestMapping("/tarification")
    public String requestCreatePageTarification() {
    	
    	return "tarification";
    	
    }
}
