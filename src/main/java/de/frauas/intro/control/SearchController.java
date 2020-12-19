package de.frauas.intro.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.frauas.intro.form.SearchForm;

@Controller("/search")
@RequestMapping("/search")
public class SearchController {
	SearchForm searchForm = new SearchForm();
	
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String get(Model model) {
		model.addAttribute("searchForm", searchForm);
		return "search";
	}
	
	@RequestMapping(value = {"", "/"},method = RequestMethod.POST)
	public String search(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {
			String id = searchForm.getInput();

		return "redirect:/bookView?id=" + id;

	}

}
