package de.frauas.intro.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.frauas.intro.data.GoogleBookAPI;
import de.frauas.intro.form.UserBookInfoForm;
import de.frauas.intro.model.Book;

@Controller
@RequestMapping("/bookView")
public class BookViewController {

	GoogleBookAPI googleBookAPI = new GoogleBookAPI();

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String viewPage(Model model, @RequestParam("user") String hash, @RequestParam("bookId") String id) {
		try {
			Book book = GoogleBookAPI.getBookByID(id);
			book.setData();
			book.setImgUri();
			model.addAttribute("infoForm", new UserBookInfoForm(hash));
			model.addAttribute("book", book);
			return "bookView";
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/error/notfound";
	}
	


}
