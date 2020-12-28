package de.frauas.intro.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.frauas.intro.DAO.GoogleBookAPI;
import de.frauas.intro.form.SearchForm;
import de.frauas.intro.form.SearchResultSummary;
import de.frauas.intro.model.SearchType;
import de.frauas.intro.util.UriUtil;

@Controller
@RequestMapping("/search")
public class SearchController {
	SearchResultSummary summary;
	
	@RequestMapping(value = {"/search"}, method = RequestMethod.GET)
	public String get(Model model,  @RequestParam("user") String userHash) {
		SearchForm searchForm = new SearchForm();
		searchForm.setUser(userHash);
		model.addAttribute("searchForm", searchForm);

		return "search/search";
	}
	
	@RequestMapping(value = {"/find"},method = RequestMethod.GET)
	public String search(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {
		String query = searchForm.getInput();
		System.out.println("AC" + searchForm.getUser());
		
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
		System.out.println("1 " + summary.getTotalItems());
		
		return displayResults(model, searchForm.getUser()); //displayResults(model, searchForm.getUser());

	}
	
	@RequestMapping(value = {"/results"},method = RequestMethod.GET)
	public String displayResults(Model model, @RequestParam("user") String userHash) {
		System.out.println("2" + summary.getTotalItems());
		model.addAttribute("bookList", summary.getItems());
		System.out.println("3");
		System.out.println(summary.getItems()[0].getVolumeInfo().getTitle());
		String uri = "search/results";
		return uri;


	}

}
