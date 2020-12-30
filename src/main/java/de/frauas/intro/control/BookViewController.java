package de.frauas.intro.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import de.frauas.intro.DAO.GoogleBookAPI;
import de.frauas.intro.model.Book;

@Controller
@RequestMapping("/bookView")
public class BookViewController {

	GoogleBookAPI googleBookAPI = new GoogleBookAPI();

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String viewPage(Model model, @RequestParam("hash") String hash, @RequestParam("id") String id) {
		try {
			Book book = googleBookAPI.getBookByID(id);
			book.setData();

			book.setImgUri();
			model.addAttribute("book", book);
			return "bookView";
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/error/notfound";
	}


}
