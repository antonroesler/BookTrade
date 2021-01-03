package de.frauas.intro.control;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import de.frauas.intro.DAO.BookDAO;
import de.frauas.intro.DAO.UserDAO;
import de.frauas.intro.DAO.UserDatabase;
import de.frauas.intro.form.AddForm;
import de.frauas.intro.form.LoginForm;
import de.frauas.intro.form.UserHashForm;
import de.frauas.intro.model.User;
import de.frauas.intro.util.UriUtil;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	UserDAO userDB;
	
	@Autowired
	UserDatabase userDatabase;
	
	BookDAO bookDAO = new BookDAO();

	
	
	@RequestMapping(value = "/login" ,method = RequestMethod.GET)
	public String loginPage(Model model) {
		LoginForm loginForm = new LoginForm();
		model.addAttribute("loginForm", loginForm);
		System.out.println("hi");
		return "user/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, @ModelAttribute("loginForm") LoginForm loginForm) {
		System.out.println("hi3");
		
		User user = new User(loginForm.getUsername(), loginForm.getPassword());
		if (userDatabase.checkUserPassword(user.getHash(), user.getPassword())) {
			
			String uri = "redirect:/my?" + UriUtil.addHeader("user", user.getHash());
			return uri;
		}
		return "user/login";
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
		userDatabase.addUser(user);
		return "redirect:/user/login";
	}
	
	@RequestMapping(value = "/view" ,method = RequestMethod.GET)
	public String viewUser(Model model, @RequestParam("search") String userHash) {
		User user = userDatabase.getUser(userHash);
		model.addAttribute("userHashForm", new UserHashForm(userHash));
		model.addAttribute("books", bookDAO.getBooksFromUser(userHash, UserBookCategory.OWNED));
		model.addAttribute("user", user);
		return "user/view";
	}
	
	@RequestMapping(value = "/inquiry", method = RequestMethod.GET)
	@ResponseBody
	public String inquiryOld(Model model, @ModelAttribute("userHashForm") UserHashForm hashForm) {
		return hashForm.getId() + hashForm.getHash();
	}

	@RequestMapping(value = "/inquiry", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String inquiry(Model model, @RequestBody UserHashForm userHashForm) {
		System.out.println(userHashForm.getHash() + " " + userHashForm.getId());
		String userHash = userHashForm.getHash();
		User user = userDatabase.getUser(userHash);
		model.addAttribute("userHashForm", new UserHashForm(userHash));
		model.addAttribute("books", bookDAO.getBooksFromUser(userHash, UserBookCategory.OWNED));
		model.addAttribute("user", user);
		return "user/view";
	}
}
