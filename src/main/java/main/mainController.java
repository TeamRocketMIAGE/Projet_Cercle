package main;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class mainController {

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String requestCreatePageIndex1() {

		
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



	@RequestMapping("/user_page")
	public String requestCreatePageUserHome() {

		
		return "user_page";

	}

	@RequestMapping("/tarification")
	public String requestCreatePageTarification() {

		return "tarification";

	}
	
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
	 
	   return (container -> {
	        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
	 
	        container.addErrorPages( error404Page);
	   });
	}
	

	

}
