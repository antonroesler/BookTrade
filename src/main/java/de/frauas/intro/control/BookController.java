package de.frauas.intro.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.frauas.intro.DAO.BookDAO;
import de.frauas.intro.form.BookForm;
import de.frauas.intro.model.Book;

@Controller
@RequestMapping(value = "/")
public class BookController {
	BookDAO bookDAO = new BookDAO();

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String mainPage(Model model) {
		model.addAttribute("books", bookDAO.getBooks());
		return "index";
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
	public String addBook(Model model, @ModelAttribute("studentForm") BookForm bookForm) {

		bookDAO.addBook(bookForm.getTitle(), bookForm.getIsbn(), bookForm.getAuthor());

		return "redirect:/index";

	}
	
	@RequestMapping(value = { "/addBookExtra" }, method = RequestMethod.POST)
	public String addBookExtra(Model model, @ModelAttribute("studentForm") BookForm bookForm) {

		bookDAO.addBookFull(bookForm.getTitle(), bookForm.getIsbn(), bookForm.getAuthor(), bookForm.getGoogleBooksReferenceUri(), 
				bookForm.getBookAbstract(), bookForm.getPublisher());

		return "redirect:/index";

	}

}
