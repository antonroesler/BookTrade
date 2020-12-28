package de.frauas.intro.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import de.frauas.intro.DAO.GoogleBookAPI;
import de.frauas.intro.form.BookSummary;

@Controller
@RequestMapping("/bookView")
public class BookViewController {
	
	GoogleBookAPI googleBookAPI = new GoogleBookAPI();
	
	@RequestMapping(method = RequestMethod.GET)
	public String viewPageID(Model model, @RequestParam("id") String id) {
		try {
			BookSummary book = googleBookAPI.getBookByID(id);
			book.setAut(book.authors());
			book.setimageUri();
			model.addAttribute("book", book);
			return "bookView";
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/error/notfound";
		
		
	}
	
	
//	@RequestMapping(value = "/query", method = RequestMethod.GET)
//	@ResponseBody
//	public String viewPageQuery(Model model, @RequestParam("q") String q) {
//		return googleBookAPI.query(q);
//
//	}
//	@RequestMapping(method = RequestMethod.GET)
//	@ResponseBody
//	public String viewPageQuery(@RequestParam("q") String query) {
//		return googleBookAPI.query(query);
//	}
	

}
