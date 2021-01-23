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

/**
 * The main controller handles the overview page (/my) for a logged in user.
 * 
 * @author Anton Roesler
 *
 */
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
	 * @return the user's main page or 404 error page if the session id is not
	 *         valid.
	 */
	@RequestMapping(value = { "/my" }, method = RequestMethod.GET)
	public String mainPage(Model model, @RequestParam("user") String session) {
		User user = sessionHandler.getUser(session);
		if (user == null) {
			// If user doesn't exist in the DB, the session will be dropped.
			return "redirect:/logout?" + UriUtil.addUserHeader(session);
		}
		// Get the books from the user. Ids need to be turned into real book objects by querying th GoogleBooksAPI.
		ArrayList<Book> ownedBooks = GoogleBookAPI
				.getAllBooksById(userDatabase.getBooksFromUser(user.getUsername(), UserBookCategory.OWNED));
		ArrayList<Book> wantedBooks = GoogleBookAPI
				.getAllBooksById(userDatabase.getBooksFromUser(user.getUsername(), UserBookCategory.WANTED));
		if (ownedBooks != null) {
			model.addAttribute("books", ownedBooks);
			model.addAttribute("booksWanted", wantedBooks);
			UserBookInfoForm infoForm = new UserBookInfoForm(session);
			model.addAttribute("username", user.getUsername());
			model.addAttribute("infoForm", infoForm);
			return "my";
		} else {
			// If owned books is null, something is wrong and the user will be logged out.
			return "redirect:/logout?" + UriUtil.addUserHeader(session);
		}

	}

	@RequestMapping(value = "/my", method = RequestMethod.POST)
	public String changeListOfBook(Model model, @ModelAttribute("infoForm") UserBookInfoForm infoForm) {
		User user = sessionHandler.getUser(infoForm.getUser());
		System.out.println("User: " + user.getUsername() + " Book: " + infoForm.getBookId());
		userDatabase.changeBook(user.getUsername(), infoForm.getBookId());
		return "redirect:/my?" + UriUtil.addUserHeader(infoForm.getUser());
	}

	/**
	 * This method is used to get back to the main page from other html pages.
	 * Usually by pressing a 'back' button.
	 * 
	 * @param session: The session ID of the active session.
	 * @return the user's main page or 404 error page if the user session id is not
	 *         valid.
	 */
	@RequestMapping(value = "/back", method = RequestMethod.GET)
	public String back(Model model, @RequestParam("user") String session) {
		return "redirect:/my?" + UriUtil.addUserHeader(session);
	}

	/**
	 * Used to delete books from a user's list.
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(Model model, @RequestBody UserBookInfoForm infoForm) {
		User user = sessionHandler.getUser(infoForm.getUser());
		userDatabase.delteBookFormUserList(user.getUsername(), infoForm.getBookId(), UserBookCategory.WANTED);
		userDatabase.delteBookFormUserList(user.getUsername(), infoForm.getBookId(), UserBookCategory.OWNED);
		System.out.println("TRY TO DELETE");

	}

	/**
	 * Used to logout a user and drop the current user's session.
	 * @param session The session id of the session to be ended.
	 * @return redirects to login page.
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model, @RequestParam("user") String session) {
		sessionHandler.dropSession(session);
		return "redirect:/user/login";
	}

}
