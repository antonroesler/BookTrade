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

import de.frauas.intro.DAO.GoogleBookAPI;
import de.frauas.intro.DAO.UserDatabase;
import de.frauas.intro.form.UserBookInfoForm;
import de.frauas.intro.model.User;
import de.frauas.intro.model.UserBookCategory;
import de.frauas.intro.util.UriUtil;

/**
 * A Controller to handle HTTP requests for pages regarding the user. Like
 * register, login or to view users.
 * 
 * @author Anton Roesler
 *
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	UserDatabase userDatabase;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model) {
		User user = new User(); // User acts as a form.
		model.addAttribute("loginForm", user);
		return "user/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, @ModelAttribute("loginForm") User user) {
		if (userDatabase.checkUserPassword(user.getHash(), user.getPassword())) {
			String uri = "redirect:/my?" + UriUtil.addHeader("user", user.getHash());
			return uri;
		}
		return "user/login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerPage(Model model) {
		User user = new User(); // User acts as a form.
		model.addAttribute("loginForm", user);
		return "user/register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(Model model, @ModelAttribute("loginForm") User user, HttpServletResponse response) {
		userDatabase.addUser(user);
		return "redirect:/user/login";
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viewUser(Model model, @RequestParam("user") String viewingUser,
			@RequestParam("search") String viewedUser) {
		User user = userDatabase.getUser(viewedUser);
		model.addAttribute("viewingUser", new UserBookInfoForm(viewingUser));
		model.addAttribute("viewedUser", new UserBookInfoForm(viewedUser));
		model.addAttribute("books",
				GoogleBookAPI.getAllBooksById(userDatabase.getBooksFromUser(viewedUser, UserBookCategory.OWNED)));
		model.addAttribute("user", user);
		return "user/view";
	}

	@RequestMapping(value = "/inquiry", method = RequestMethod.GET)
	@ResponseBody
	public String inquiryOld(Model model, @ModelAttribute("userHashForm") UserBookInfoForm infoForm) {
		return infoForm.getBookId() + infoForm.getUser();
	}

	@RequestMapping(value = "/inquiry", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String inquiry(Model model, @RequestBody UserBookInfoForm infoForm) {
		String userHash = infoForm.getUser();
		User user = userDatabase.getUser(userHash);
		model.addAttribute("viewingUser", new UserBookInfoForm(userHash));
		model.addAttribute("books",
				GoogleBookAPI.getAllBooksById(userDatabase.getBooksFromUser(userHash, UserBookCategory.OWNED)));
		model.addAttribute("user", user);
		return "user/view";
	}

}
