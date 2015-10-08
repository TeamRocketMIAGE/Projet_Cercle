package main;





import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class ProjetCercleApplication extends WebMvcConfigurerAdapter implements CommandLineRunner {

	
    private static final Logger log = LoggerFactory.getLogger(ProjetCercleApplication.class);

    @Autowired
    UserRepository userRepository;
	
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/inscription").setViewName("inscription");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/telechargement").setViewName("telechargement");
        registry.addViewController("/user_page").setViewName("user_page");
        registry.addViewController("/user_param").setViewName("user_param");
        registry.addViewController("/cercle_page").setViewName("cercle_page");
        registry.addViewController("/404").setViewName("404");
        registry.addViewController("/401").setViewName("401");
        registry.addViewController("/500").setViewName("500");
    }
	
    public static void main(String[] args) {
        SpringApplication.run(ProjetCercleApplication.class, args);
    }
    
    @Override
    public void run(String... strings) throws Exception {
        
        
        // ajout d'utilisateur dans la base de données
    	//
        Utilisateur uUser = new Utilisateur("user", "abc");
        Utilisateur uBob = new Utilisateur("Bob", "abc");
        Utilisateur uChris = new Utilisateur("Chris", "abc");
        Utilisateur uFranck = new Utilisateur("Franck", "abc");
        
       
        Utilisateur uJohnny = new Utilisateur("Johnny", "abc");
        Utilisateur uHugo = new Utilisateur("Hugo", "abc");
        Utilisateur uEmma = new Utilisateur("Emma", "abc");
        Utilisateur uMarie= new Utilisateur("Marie", "abc");
        Utilisateur uPrincesse= new Utilisateur("Princesse", "abc");        
        
       
        
        userRepository.save(uUser);
        userRepository.save(uBob);
        userRepository.save(uChris);
        userRepository.save(uFranck);
        userRepository.save(uJohnny);
        userRepository.save(uHugo);
        userRepository.save(uEmma);
        userRepository.save(uMarie);
        userRepository.save(uPrincesse);    
      
        uBob.setMail("gauthier@scampini.fr");
        uBob.setPrenom("bobinou");
        uBob.setNom("leLegendaire");
        
        
        uBob.addContact(uChris);
        userRepository.save(uBob);
        uChris.addContact(uBob);
        userRepository.save(uChris);
        
        
        uFranck.addContact(uChris);
        userRepository.save(uFranck);
        
        uChris.addContact(uFranck);
        userRepository.save(uChris);
        
        
        uFranck.addContact(uBob);
        userRepository.save(uFranck);
        uBob.addContact(uFranck);
        userRepository.save(uBob);
        
        

        /*
        // fetch all users
        log.info("Utilisateurs found with findAll():");
        log.info("-------------------------------");
        for (Utilisateur utilisateur : userRepository.findAll()) {
            log.info(utilisateur.toString());
        }
        System.out.println();
        */

    }
    
}
