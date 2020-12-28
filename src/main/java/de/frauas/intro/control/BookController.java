package de.frauas.intro.control;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.api.services.storage.Storage.BucketAccessControls.Get;

import de.frauas.intro.DAO.BookDAO;
import de.frauas.intro.DAO.UserDAO;
import de.frauas.intro.form.BookForm;
import de.frauas.intro.form.BookSummary;
import de.frauas.intro.form.LoginForm;
import de.frauas.intro.form.SearchForm;
import de.frauas.intro.form.UserHashForm;
import de.frauas.intro.model.Book;
import de.frauas.intro.model.User;

@Controller
@RequestMapping(value = "/")
public class BookController {
	BookDAO bookDAO = new BookDAO();
	
	@Autowired
	UserDAO userDB;
	
	@RequestMapping(value = {"", "/", "/index"}, method = RequestMethod.GET)
	public String Get() {
		return "redirect:/user/login";
	}

	@RequestMapping(value = { "/my" }, method = RequestMethod.GET)
	public String mainPage(Model model, @RequestParam("user") String userHash, HttpServletResponse response) {

		ArrayList<BookSummary> bookSummaries = userDB.getBooks(userHash);
		
		if (bookSummaries != null) {
		model.addAttribute("books", bookSummaries);
		UserHashForm hashForm = new UserHashForm();
		hashForm.setHash(userHash);
		model.addAttribute("userHashForm",hashForm);
		return "my";
		} 
		else return "redirect:/user/login";
		
	}
	
	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String search(Model model,  @ModelAttribute("userHashForm") UserHashForm hashForm) {	
		String uriString = "redirect:/search/search?user="+ hashForm.getHash();
		return uriString;
		
	}

	@RequestMapping(value = { "/makeSome" }, method = RequestMethod.POST)
	public String makeSomeSampleBooks(Model model) {
		bookDAO.createSomeNewBooks();
		return "redirect:/index";
	}

	@RequestMapping(value = { "/addBook" }, method = RequestMethod.GET)
	public String addBookpage(Model model) {
		BookForm bookForm = new BookForm();
		model.addAttribute("bookForm", bookForm);
		return "addBook";

	}

	@RequestMapping(value = { "/addBook" }, method = RequestMethod.POST)
	public String addBook(Model model, @ModelAttribute("bookForm") BookForm bookForm) {

		bookDAO.addBook(bookForm.getTitle(), bookForm.getIsbn(), bookForm.getAuthor());

		return "redirect:/index";

	}
	
	@RequestMapping(value = { "/addBookExtra" }, method = RequestMethod.POST)
	public String addBookExtra(Model model, @ModelAttribute("bookForm") BookForm bookForm) {

		bookDAO.addBookFull(bookForm.getTitle(), bookForm.getIsbn(), bookForm.getAuthor(), bookForm.getGoogleBooksReferenceUri(), 
				bookForm.getBookAbstract(), bookForm.getPublisher());

		return "redirect:/index";

	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@ResponseBody
	public String allUsers(@RequestParam("user") String userHash) throws InterruptedException, ExecutionException {
		User user = userDB.getUser(userHash);
		
		return user.getUsername();//userDB.getAllUsers().get(0).getUsername();
	}
	

}
