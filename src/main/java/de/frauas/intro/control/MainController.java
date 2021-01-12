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

import de.frauas.intro.DAO.BookDAO;
import de.frauas.intro.DAO.UserDatabase;
import de.frauas.intro.form.SearchForm;
import de.frauas.intro.form.UserBookInfoForm;
import de.frauas.intro.model.Book;
import de.frauas.intro.model.User;
import de.frauas.intro.util.UriUtil;

@Controller
@RequestMapping(value = "/")
public class MainController {

	@Autowired
	UserDatabase userDatabase;
	
	BookDAO bookDAO = new BookDAO();

	@RequestMapping(value = { "", "/", "/index", "/logout" }, method = RequestMethod.GET)
	public String Get() {
		return "redirect:/user/login";
	}

	/**
	 * The main page for each user. 
	 * 
	 * @param model 
	 * @param userHash: The hash value that uniquely identifies a user. User.getHash()
	 * @return the user's main page or 404 error page if the user hash is not valid.
	 */
	@RequestMapping(value = { "/my" }, method = RequestMethod.GET)
	public String mainPage(Model model, @RequestParam("user") String userHash) {
		ArrayList<Book> ownedBooks = bookDAO.getBooksFromUser(userHash, UserBookCategory.OWNED);
		ArrayList<Book> wantedBooks = bookDAO.getBooksFromUser(userHash, UserBookCategory.WANTED);
		if (ownedBooks != null) {
			model.addAttribute("books", ownedBooks);
			model.addAttribute("booksWanted", wantedBooks);
			UserBookInfoForm hashForm = new UserBookInfoForm(userHash);
			System.out.println("Y " + hashForm.getUser());
			model.addAttribute("username", userDatabase.getUser(userHash).getUsername());
			model.addAttribute("infoForm", hashForm);
			return "my";
		} else {
			return "redirect:/user/login";
		}

	}


	@RequestMapping(value = "/my", method = RequestMethod.POST)
	public String changeListOfBook(Model model, @ModelAttribute("userHashForm") UserBookInfoForm infoForm) {
		System.out.println("User: " + infoForm.getUser() + " Book: " + infoForm.getBookId());		
		userDatabase.changeBook(infoForm.getUser(), infoForm.getBookId());
		return "redirect:/my?" + UriUtil.addUserHeader(infoForm.getUser());
	}
	

	
	/**
	 * This method is used to get back to the main page from other html pages.
	 * Usually by pressing a 'back' button.
	 * @param model
	 * @param userHash userHash: The hash value that uniquely identifies a user. User.getHash()
	 * @return the user's main page or 404 error page if the user hash is not valid.
	 */
	@RequestMapping(value = "/back", method = RequestMethod.GET)
	public String back(Model model, @RequestParam("user") String userHash) {
		return "redirect:/my?" + UriUtil.addUserHeader(userHash) ;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public String delete(Model model, @RequestBody UserBookInfoForm infoForm) {
		userDatabase.delteBookFormUserList(infoForm.getUser(), infoForm.getBookId(), UserBookCategory.WANTED);
		userDatabase.delteBookFormUserList(infoForm.getUser(), infoForm.getBookId(), UserBookCategory.OWNED);
		System.out.println("TRY TO DELETE");
		return "my" ;
	}


}
