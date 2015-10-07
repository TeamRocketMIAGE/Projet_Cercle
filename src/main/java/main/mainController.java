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




	@RequestMapping("/tarification")
	public String requestCreatePageTarification() {

		return "tarification";

	}
	
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
	 
	   return (container -> {
		   ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
	        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
	        ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
	 
	 
	        container.addErrorPages( error401Page, error404Page, error500Page);
	   });
	}
	

	

}
