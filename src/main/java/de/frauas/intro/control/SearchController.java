package de.frauas.intro.control;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.frauas.intro.DAO.GoogleBookAPI;
import de.frauas.intro.DAO.UserDatabase;
import de.frauas.intro.form.SearchForm;
import de.frauas.intro.form.SearchResultSummary;
import de.frauas.intro.form.UserBookInfoForm;
import de.frauas.intro.model.SearchType;
import de.frauas.intro.model.User;
import de.frauas.intro.util.UriUtil;

@Controller
@RequestMapping("/search")
public class SearchController {
	SearchResultSummary summary;

	@Autowired
	UserDatabase userDatabase;

	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String get(Model model, @RequestParam("user") String userHash) {
		SearchForm searchForm = new SearchForm();
		UserBookInfoForm infoForm = new UserBookInfoForm(userHash);
		searchForm.setUser(userHash);
		searchForm.setType(SearchType.ALL);
		model.addAttribute("searchForm", searchForm);
		model.addAttribute("infoForm", infoForm);
		return "search/search";
	}

	@RequestMapping(value = { "/user" }, method = RequestMethod.POST)
	public String getUserSearchPage(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {
		System.out.println(searchForm.getUser());
		String activeUserHash = searchForm.getUser();
		String searchedUserHash = searchForm.getInput();
		return "redirect:/user/view?" + UriUtil.addUserHeader(activeUserHash)
				+ UriUtil.addHeader("search", searchedUserHash);
	}

	@RequestMapping(value = { "/find" }, method = RequestMethod.GET)
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
		return displayResults(model, searchForm.getUser()); // displayResults(model, searchForm.getUser());
	}

	@RequestMapping(value = { "/results" }, method = RequestMethod.GET)
	public String displayResults(Model model, @RequestParam("user") String userHash) {
		model.addAttribute("bookList", summary.getItems());
		UserBookInfoForm infoForm = new UserBookInfoForm(userHash);
		model.addAttribute("userHashForm", infoForm);
		String uri = "search/results";
		return uri;
	}

	@RequestMapping(value = "/results", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String addBook(Model model, @RequestBody UserBookInfoForm infoForm) {
		userDatabase.addBookToUser(infoForm.getUser(), infoForm.getBookId(), UserBookCategory.OWNED);
		infoForm = new UserBookInfoForm(infoForm.getUser());
		model.addAttribute("userHashForm", infoForm);
		return "search/results";
	}

	@RequestMapping(value = "/findUser", method = RequestMethod.GET)
	public String findUsers(Model model, @ModelAttribute("userHashForm") UserBookInfoForm infoForm) {
		System.out.println("LOOKING FOR BOOK: " + infoForm.getBookId());
		System.out.println("LOOKING FOR BOOK: " + infoForm.getUser());
		ArrayList<User> users = userDatabase.getUsersWithBook(infoForm.getBookId());
		model.addAttribute("infoForm", infoForm);
		if (users.isEmpty()) {
			return "/error/noUser";
		}
		SearchForm searchForm = new SearchForm();
		searchForm.setUser(infoForm.getUser());
		model.addAttribute("users", users);
		model.addAttribute("searchForm", searchForm);
		return "search/findUser";
	}

}
