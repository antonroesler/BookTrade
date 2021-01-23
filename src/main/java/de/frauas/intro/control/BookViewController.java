package de.frauas.intro.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.frauas.intro.data.GoogleBookAPI;
import de.frauas.intro.form.UserBookInfoForm;
import de.frauas.intro.model.Book;
import de.frauas.intro.model.User;
import de.frauas.intro.session.SessionHandler;

@Controller
@RequestMapping("/bookView")
public class BookViewController {

	@Autowired
	SessionHandler sessionHandler;

	GoogleBookAPI googleBookAPI = new GoogleBookAPI();

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String viewPage(Model model, @RequestParam("user") String session, @RequestParam("bookId") String id) {
		try {
			Book book = GoogleBookAPI.getBookByID(id);
			book.setData();
			book.setImgUri();
			model.addAttribute("infoForm", new UserBookInfoForm(session));
			model.addAttribute("book", book);
			return "bookView";
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/error/notfound";
	}

}
