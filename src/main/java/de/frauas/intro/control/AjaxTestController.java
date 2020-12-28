package de.frauas.intro.control;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.frauas.intro.form.SearchForm;

@Controller
@RequestMapping("/ajaxTest")
public class AjaxTestController {
	
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String get() {
		return "ajaxTest";
	}
	
	
	@RequestMapping(value = "/id", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String searchById(@RequestBody SearchForm searchForm) {
		System.out.println("CALL");
		return "redirect:/results?q=" + searchForm.getInput();
		

	}
	

}
