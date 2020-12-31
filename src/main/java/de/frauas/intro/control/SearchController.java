package de.frauas.intro.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.frauas.intro.DAO.GoogleBookAPI;
import de.frauas.intro.DAO.UserDAO;
import de.frauas.intro.DAO.UserDatabase;
import de.frauas.intro.form.AddForm;
import de.frauas.intro.form.SearchForm;
import de.frauas.intro.form.SearchResultSummary;
import de.frauas.intro.form.UserHashForm;
import de.frauas.intro.model.SearchType;
import de.frauas.intro.util.UriUtil;

@Controller
@RequestMapping("/search")
public class SearchController {
	SearchResultSummary summary;
	
	@Autowired
	UserDatabase userDatabase;
	
	@RequestMapping(value = {"/search"}, method = RequestMethod.GET)
	public String get(Model model,  @RequestParam("user") String userHash) {
		SearchForm searchForm = new SearchForm();
		searchForm.setUser(userHash);
		model.addAttribute("searchForm", searchForm);

		return "search/search";
	}
	
	@RequestMapping(value = {"/e"}, method = RequestMethod.GET)
	public String getError() {
		int x = 3/0;
		return "index";
	}
	
	@RequestMapping(value = {"/find"},method = RequestMethod.GET)
	public String search(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {
		String query = searchForm.getInput();
		
		switch (searchForm.getType()) {
		case AUTHOR:
			summary = GoogleBookAPI.queryByAuthor(query);
			break;
		case TITLE:
			summary = GoogleBookAPI.queryByTitle(query);
			break;
		case ISBN:
			summary = GoogleBookAPI.queryByISBN(query);
			break;
			
		default:
			summary = GoogleBookAPI.query(query);
			break;
		}
		summary.setData();
		System.out.println(summary.getItems()[0].authors());
		return displayResults(model, searchForm.getUser()); //displayResults(model, searchForm.getUser());

	}
	
	@RequestMapping(value = {"/results"},method = RequestMethod.GET)
	public String displayResults(Model model, @RequestParam("user") String userHash) {
		model.addAttribute("bookList", summary.getItems());
		UserHashForm hashForm = new UserHashForm(userHash);
		model.addAttribute("userHashForm",hashForm);
		String uri = "search/results";
		return uri;


	}
	
	
	@RequestMapping(value = "/results", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String addBook(Model model, @RequestBody AddForm addForm) {
		userDatabase.addBookToUser(addForm.getUserHash(), addForm.getBookId(), UserBookCategory.OWNED);
		UserHashForm hashForm = new UserHashForm(addForm.getUserHash());
		model.addAttribute("userHashForm",hashForm);
		System.out.println("Book added");
		return "search/results";
	

	}
	
	@RequestMapping(value = "/back", method = RequestMethod.GET)
	public String backToMyBooks(Model model, @RequestParam("hash") String hash) {
		
		return "redirect:/my?" + UriUtil.addUserHeader(hash);
	

	}
	
	

}
