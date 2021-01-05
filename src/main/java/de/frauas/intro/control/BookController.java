package de.frauas.intro.control;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
			UserBookInfoForm hashForm = new UserBookInfoForm(userHash);
			System.out.println("Y " + hashForm.getHash());
			model.addAttribute("username", userDatabase.getUser(userHash).getUsername());
			model.addAttribute("infoForm", hashForm);
			return "my";
		} else {
			return "redirect:/user/login";
		}

	}



	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String search(Model model, @ModelAttribute("userHashForm") UserBookInfoForm hashForm) {
		String uriString = "redirect:/search/search?user=" + hashForm.getHash();
		return uriString;

	}

	@RequestMapping(value = "/my", method = RequestMethod.POST)
	public String changeListOfBook(Model model, @ModelAttribute("userHashForm") UserBookInfoForm infoForm) {
		System.out.println("User: " + infoForm.getHash() + " Book: " + infoForm.getBookId());		
		userDatabase.changeBook(infoForm.getHash(), infoForm.getBookId());
		return mainPage(model, infoForm.getHash());
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String findUsers(Model model, @ModelAttribute("userHashForm") UserBookInfoForm infoForm) {
		System.out.println("LOOKING FOR BOOK: " + infoForm.getBookId());
		System.out.println("LOOKING FOR BOOK: " + infoForm.getHash());
		ArrayList<User> users = userDatabase.getUsersWithBook(infoForm.getBookId());
		model.addAttribute("infoForm", infoForm);
		if (users.isEmpty()) {
			return "/error/noUser";
		}
		SearchForm searchForm = new SearchForm();
		searchForm.setUser(infoForm.getHash());
		model.addAttribute("users", users);
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
