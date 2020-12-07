package de.frauas.intro.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.frauas.intro.DAO.BookDAO;
import de.frauas.intro.model.Book;

@Controller
@RequestMapping(value = "/")
public class BookController {
	BookDAO bookDAO = new BookDAO();
	
	
	@RequestMapping(value = { "/listBooks" }, method = RequestMethod.GET)
	public String listStudents(Model model) {
		bookDAO.addBook("Stats", "1324", "Name");
		model.addAttribute("books", bookDAO.getBooks());
		return "listBooks";
	}

}
