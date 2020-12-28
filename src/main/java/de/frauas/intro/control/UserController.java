package de.frauas.intro.control;

import java.net.URI;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.internal.Excluder;

import de.frauas.intro.DAO.UserDAO;
import de.frauas.intro.form.LoginForm;
import de.frauas.intro.model.User;
import de.frauas.intro.util.UriUtil;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	UserDAO userDB;
	
	
	@RequestMapping(value = "/login" ,method = RequestMethod.GET)
	public String loginPage(Model model) {
		LoginForm loginForm = new LoginForm();
		model.addAttribute("loginForm", loginForm);
		return "user/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, @ModelAttribute("loginForm") LoginForm loginForm) {
		System.out.println("HERE");
		if (loginForm.getUsername().equals("anton") && loginForm.getPassword().equals("123")) {
			
			return "redirect:/index";
		}
		return "redirect:/user/login";
	}

	@RequestMapping(value = "/register" ,method = RequestMethod.GET)
	public String registerPage(Model model) {
		LoginForm loginForm = new LoginForm();
		model.addAttribute("loginForm", loginForm);
		return "user/register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(Model model, @ModelAttribute("loginForm") LoginForm loginForm, HttpServletResponse response) {
		User user = new User(loginForm.getUsername(), loginForm.getPassword());
		userDB.addUser(user);
		
		String uri = "redirect:/my?" + UriUtil.addHeader("user", user.getHash());
		return uri;
	}
	

}
