package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class ProjetCercleApplication extends WebMvcConfigurerAdapter {

	
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/inscription").setViewName("inscription");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/telechargement").setViewName("telechargement");
        registry.addViewController("/user_page").setViewName("user_page");
        registry.addViewController("/404").setViewName("404");
    }
	
    public static void main(String[] args) {
        SpringApplication.run(ProjetCercleApplication.class, args);
    }
}
