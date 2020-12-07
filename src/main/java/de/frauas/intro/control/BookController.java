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
	
	
	@RequestMapping(value = { "/listBooks" }, method = RequestMethod.GET)
	public String listBooks(Model model) {
		model.addAttribute("books", bookDAO.getBooks());
		return "listBooks";
	}
	
	@RequestMapping(value = { "/makeSome" }, method = RequestMethod.POST)
	public String makeSome(Model model) {
		bookDAO.createSomeNewBooks();
		return "redirect:/listBooks";
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
		
		return "redirect:/listBooks";
		
	}

}
