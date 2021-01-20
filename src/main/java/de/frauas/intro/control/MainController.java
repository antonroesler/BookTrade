package de.frauas.intro.control;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.frauas.intro.data.GoogleBookAPI;
import de.frauas.intro.data.UserDatabase;
import de.frauas.intro.form.UserBookInfoForm;
import de.frauas.intro.model.Book;
import de.frauas.intro.model.User;
import de.frauas.intro.model.UserBookCategory;
import de.frauas.intro.session.SessionHandler;
import de.frauas.intro.util.UriUtil;

@Controller
@RequestMapping(value = "/")
public class MainController {

	@Autowired
	UserDatabase userDatabase;

	@Autowired
	SessionHandler sessionHandler;


	@RequestMapping(value = { "", "/", "/index" }, method = RequestMethod.GET)
	public String Get() {
		return "redirect:/user/login";
	}

	/**
	 * The main page for each user.
	 *
	 * @param model
	 * @param session: The session ID of the active session.
	 * @return the user's main page or 404 error page if the user hash is not valid.
	 */
	@RequestMapping(value = { "/my" }, method = RequestMethod.GET)
	public String mainPage(Model model, @RequestParam("user") String session) {
		User user = sessionHandler.getUser(session);
		if (user == null) {
			return "redirect:/user/login";
		}
		ArrayList<Book> ownedBooks = GoogleBookAPI.getAllBooksById(userDatabase.getBooksFromUser(user.getUsername(), UserBookCategory.OWNED));
		ArrayList<Book> wantedBooks = GoogleBookAPI.getAllBooksById(userDatabase.getBooksFromUser(user.getUsername(), UserBookCategory.WANTED));
		if (ownedBooks != null) {
			model.addAttribute("books", ownedBooks);
			model.addAttribute("booksWanted", wantedBooks);
			UserBookInfoForm hashForm = new UserBookInfoForm(session);
			model.addAttribute("username", user.getUsername());
			model.addAttribute("infoForm", hashForm);
			return "my";
		} else {
			return "redirect:/user/login";
		}

	}


	@RequestMapping(value = "/my", method = RequestMethod.POST)
	public String changeListOfBook(Model model, @ModelAttribute("userHashForm") UserBookInfoForm infoForm) {
		User user = sessionHandler.getUser(infoForm.getUser());
		System.out.println("User: " + user.getUsername() + " Book: " + infoForm.getBookId());
		userDatabase.changeBook(user.getUsername(), infoForm.getBookId());
		return "redirect:/my?" + UriUtil.addUserHeader(infoForm.getUser());
	}



	/**
	 * This method is used to get back to the main page from other html pages.
	 * Usually by pressing a 'back' button.
	 * @param model
	 * @param session: The session ID of the active session.
	 * @return the user's main page or 404 error page if the user hash is not valid.
	 */
	@RequestMapping(value = "/back", method = RequestMethod.GET)
	public String back(Model model, @RequestParam("user") String session) {
		return "redirect:/my?" + UriUtil.addUserHeader(session) ;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(Model model, @RequestBody UserBookInfoForm infoForm) {
		User user = sessionHandler.getUser(infoForm.getUser());
		userDatabase.delteBookFormUserList(user.getUsername(), infoForm.getBookId(), UserBookCategory.WANTED);
		userDatabase.delteBookFormUserList(user.getUsername(), infoForm.getBookId(), UserBookCategory.OWNED);
		System.out.println("TRY TO DELETE");

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model, @RequestParam("user") String session) {
		sessionHandler.dropSession(session);
		return "redirect:/user/login";
	}

}
