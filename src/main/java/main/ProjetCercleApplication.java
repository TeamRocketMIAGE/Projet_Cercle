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
    
    @Autowired
    CercleRepository cercleRepository;
	
    @Autowired
    FichierRepository fichierRepository;
    
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
        registry.addViewController("/cercle_param").setViewName("cercle_param");
        registry.addViewController("/404").setViewName("404");
        registry.addViewController("/401").setViewName("401");
        registry.addViewController("/500").setViewName("500");
    }
	
    public static void main(String[] args) {
        SpringApplication.run(ProjetCercleApplication.class, args);
    }
    
    @Override
    public void run(String... strings) throws Exception {
        
        
        /*
         * 
         *  ajout d'utilisateurs dans la base de données
         *  
         */
    	
        Utilisateur uUser = new Utilisateur("user", "abc");
        Utilisateur uBob = new Utilisateur("Bob", "abc");
        uBob.setMail("gauthier@scampini.fr");
        uBob.setPrenom("bobinou");
        uBob.setNom("leLegendaire");
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
      

        // Initialisation des listes de contact des utilisateurs principaux
        //
        
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
         * 
         *  ajout de cercles dans la base de données
         *  
         */
        
        
        // Cercle 1
        //
        Cercle c1 = new Cercle();
        c1.addAdministrateur(uFranck);
        c1.addUtilisateur(uBob);
        c1.addUtilisateur(uChris);
        c1.setName("Google");
        c1.setDescription("Big brother is watching you !");
        cercleRepository.save(c1);
        
        uFranck.addCercles_admin(c1);
        uChris.addCercles_membre(c1);
        uBob.addCercles_membre(c1);
        userRepository.save(uFranck);
        userRepository.save(uChris);
        userRepository.save(uBob);
        
        
        // Cercle 2
        //        
        Cercle c2 = new Cercle();
        c2.addAdministrateur(uBob);
        c2.addAdministrateur(uChris);
        c2.addUtilisateur(uFranck);        
        c2.addUtilisateur(uPrincesse);
        c2.addUtilisateur(uEmma);
        c2.addUtilisateur(uJohnny);
        c2.setName("Apple");
        c2.setDescription("Ici, c'est pas d'Android !");
        cercleRepository.save(c2);
        
        uBob.addCercles_admin(c2);
        uChris.addCercles_admin(c2);
        uFranck.addCercles_membre(c2);
        uPrincesse.addCercles_membre(c2);
        uEmma.addCercles_membre(c2);
        uJohnny.addCercles_membre(c2);
        userRepository.save(uFranck);
        userRepository.save(uChris);
        userRepository.save(uBob);
        userRepository.save(uPrincesse);
        userRepository.save(uEmma);
        userRepository.save(uJohnny);
        
        /*
        Cercle c3 = new Cercle();
        c1.addAdministrateur(uChris);
        c1.addUtilisateur(uFranck);
        c1.addUtilisateur(uBob);
        c1.setName("La branche qui se charge du développement Android de chez Google");
        c1.setDescription("Ici, c'est pas d'iphone, nous on ne fait pas de la merde !");
        
        c1.addSousCercle(c3);
        
        cercleRepository.save(c1);
        cercleRepository.save(c2);
        cercleRepository.save(c3);
        */
        
        

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
