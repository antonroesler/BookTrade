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
import de.frauas.intro.DAO.GoogleBookAPI;
import de.frauas.intro.DAO.InquiryDAO;
import de.frauas.intro.DAO.UserDatabase;
import de.frauas.intro.form.LoginForm;
import de.frauas.intro.form.UserBookInfoForm;
import de.frauas.intro.model.Inquiry;
import de.frauas.intro.model.User;
import de.frauas.intro.model.UserBookCategory;
import de.frauas.intro.util.UriUtil;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	
	@Autowired
	UserDatabase userDatabase;
	
	@Autowired
	BookDAO bookDAO = new BookDAO();

	@Autowired
	InquiryDAO inquiryDAO;
	
	
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
	public String viewUser(Model model, @RequestParam("user") String viewingUser, @RequestParam("search") String viewedUser) {
		User user = userDatabase.getUser(viewedUser);
		model.addAttribute("viewingUser", new UserBookInfoForm(viewingUser));
		model.addAttribute("viewedUser", new UserBookInfoForm(viewedUser));
		model.addAttribute("books", bookDAO.getBooksFromUser(viewedUser, UserBookCategory.OWNED));
		model.addAttribute("user", user);
		return "user/view";
	}
	
	@RequestMapping(value = "/inquiry", method = RequestMethod.GET)
	@ResponseBody
	public String inquiryOld(Model model, @ModelAttribute("userHashForm") UserBookInfoForm infoForm) {
		return infoForm.getBookId() + infoForm.getUser();
	}

	@RequestMapping(value = "/inquiry", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String inquiry(Model model, @RequestBody UserBookInfoForm infoForm, @RequestParam("search") String requestedUser) {
		String userHash = infoForm.getUser();
		User user = userDatabase.getUser(userHash);
		model.addAttribute("viewingUser", new UserBookInfoForm(userHash));
		model.addAttribute("books", bookDAO.getBooksFromUser(userHash, UserBookCategory.OWNED));
		model.addAttribute("user", user);
		System.out.println("BookID: "+infoForm.getBookId());
		System.out.println("User1: "+infoForm.getUser());
		System.out.println("User2: "+ requestedUser);
		inquiryDAO.save(new Inquiry(user, userDatabase.getUser(requestedUser), GoogleBookAPI.getBookByID(infoForm.getBookId())));
		System.out.println("A: "+inquiryDAO.getAll().size());
		return "user/view";
	}
	
}
