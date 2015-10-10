package main;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class cerclepageController {

	@Autowired
	CercleRepository cercleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	

	
	@RequestMapping(value = "/cercle_page", method = RequestMethod.GET)
	public String requestCreatePageCercle(@RequestParam(value = "cercle") String cercle_id, Model model) {

		System.out.println("Cercle actuel : " + cercle_id);
		
		
		
		Cercle currentCercle = 	cercleRepository.findById(Long.parseLong(cercle_id));
		
		if (currentCercle != null)
		{
			model.addAttribute("currentCercle",currentCercle);
			
			return "cercle_page";
		}
		return ("redirect:/user_page");
		

	    

	

	}
}
	

