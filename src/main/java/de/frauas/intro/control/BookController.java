package de.frauas.intro.control;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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

import de.frauas.intro.DAO.BookDAO;
import de.frauas.intro.DAO.GoogleBookAPI;
import de.frauas.intro.DAO.UserDAO;
import de.frauas.intro.DAO.UserDatabase;
import de.frauas.intro.form.BookForm;
import de.frauas.intro.form.LoginForm;
import de.frauas.intro.form.SearchForm;
import de.frauas.intro.form.UserHashForm;
import de.frauas.intro.model.Book;
import de.frauas.intro.model.User;
import de.frauas.intro.util.UriUtil;

@Controller
@RequestMapping(value = "/")
public class BookController {

	@Autowired
	UserDatabase userDatabase;
	
	BookDAO bookDAO = new BookDAO();

	@RequestMapping(value = { "", "/", "/index", "/logout" }, method = RequestMethod.GET)
	public String Get() {
		return "redirect:/user/login";
	}

	@RequestMapping(value = { "/my" }, method = RequestMethod.GET)
	public String mainPage(Model model, @RequestParam("user") String userHash) {
		ArrayList<Book> ownedBooks = bookDAO.getBooksFromUser(userHash, UserBookCategory.OWNED);
		ArrayList<Book> wantedBooks = bookDAO.getBooksFromUser(userHash, UserBookCategory.WANTED);
		if (ownedBooks != null) {
			model.addAttribute("books", ownedBooks);
			model.addAttribute("booksWanted", wantedBooks);
			UserHashForm hashForm = new UserHashForm(userHash);
			model.addAttribute("username", userDatabase.getUser(userHash).getUsername());
			model.addAttribute("userHashForm", hashForm);
			return "my";
		} else {
			return "redirect:/user/login";
		}

	}



	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String search(Model model, @ModelAttribute("userHashForm") UserHashForm hashForm) {
		String uriString = "redirect:/search/search?user=" + hashForm.getHash();
		return uriString;

	}

	@RequestMapping(value = "/my", method = RequestMethod.POST)
	public String changeListOfBook(Model model, @ModelAttribute("userHashForm") UserHashForm hashForm) {
		userDatabase.changeBook(hashForm.getHash(), hashForm.getId());
		return mainPage(model, hashForm.getHash());
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String findUsers(Model model, @ModelAttribute("userHashForm") UserHashForm hashForm) {
		ArrayList<User> users = userDatabase.getUsersWithBook(hashForm.getId());
		if (users.isEmpty()) {
			return "/error/noUser";
		}
		model.addAttribute("users", users);
		SearchForm searchForm = new SearchForm();
		searchForm.setUser(hashForm.getHash());
		model.addAttribute("searchForm", searchForm);
		return "/find";
	}
	
	@RequestMapping(value = "/back", method = RequestMethod.GET)
	public String back(Model model, @RequestParam("hash") String userHash) {
		return "redirect:/my?" + UriUtil.addUserHeader(userHash) ;
	}

//	@RequestMapping(value = { "/makeSome" }, method = RequestMethod.POST)
//	public String makeSomeSampleBooks(Model model) {
//		user
//		return "redirect:/index";
//	}
//
//	@RequestMapping(value = { "/addBook" }, method = RequestMethod.GET)
//	public String addBookpage(Model model) {
//		BookForm bookForm = new BookForm();
//		model.addAttribute("bookForm", bookForm);
//		return "addBook";
//
//	}
//
//	@RequestMapping(value = { "/addBook" }, method = RequestMethod.POST)
//	public String addBook(Model model, @ModelAttribute("bookForm") BookForm bookForm) {
//
//		bookDAO.addBook(bookForm.getTitle(), bookForm.getIsbn(), bookForm.getAuthor());
//
//		return "redirect:/index";
//
//	}
//	
//	@RequestMapping(value = { "/addBookExtra" }, method = RequestMethod.POST)
//	public String addBookExtra(Model model, @ModelAttribute("bookForm") BookForm bookForm) {
//
//		bookDAO.addBookFull(bookForm.getTitle(), bookForm.getIsbn(), bookForm.getAuthor(), bookForm.getGoogleBooksReferenceUri(), 
//				bookForm.getBookAbstract(), bookForm.getPublisher());
//
//		return "redirect:/index";
//
//	}

}
