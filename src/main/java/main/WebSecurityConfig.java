package main;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/home" , "/inscription", "/telechargement", "/login","/404","/401","/500", "/tarification", "/css/**", "/fonts/**", "/js/**", "/img/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/user_page", true)                
                .permitAll()
                .and()
            .csrf().disable()
            .logout()
                .permitAll();        	
    }
    
    

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        
    	/*
    	auth
            .inMemoryAuthentication()
                .withUser("user").password("abc").roles("USER");
                */
    	auth.userDetailsService(inMemoryUserDetailsManager()/*.passwordEncoder(passwordEncoder())*/);
    }
    
    
    
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        final Properties users = new Properties();
        
        // principaux utilisateurs
        users.put("user","abc,USER,enabled"); 
        users.put("Bob","abc,USER,enabled"); 
        users.put("Chris","abc,USER,enabled"); 
        users.put("Franck","abc,USER,enabled"); 
        users.put("Sebastien","abc,USER,enabled"); 
        
        // autres
        users.put("Marie","abc,USER,enabled"); 
        users.put("Emma","abc,USER,enabled");
        users.put("Princesse","abc,USER,enabled"); 
        users.put("Hugo","abc,USER,enabled");
        users.put("Johnny","abc,USER,enabled");
        return new InMemoryUserDetailsManager(users);
    }
    /*
    @Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}*/
    

}
