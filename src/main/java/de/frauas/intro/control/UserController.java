package de.frauas.intro.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.frauas.intro.form.LoginForm;

@Controller
@RequestMapping(value = {"/login", "/register"})
public class UserController {
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String loginPage(Model model) {
		LoginForm loginForm = new LoginForm();
		model.addAttribute("loginForm", loginForm);
		return "login";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String login(Model model, @ModelAttribute("loginForm") LoginForm loginForm) {
		System.out.println("HERE");
		if (loginForm.getUsername().equals("anton") && loginForm.getPassword().equals("123")) {
			
			return "redirect:/index";
		}
		return "redirect:/login";
	}
	

}
