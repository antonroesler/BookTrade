package de.frauas.intro.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("error")
public class CustomErrorController {
	
	
	@RequestMapping("notfound")
	public String BookNotFound() {
		return "error/notfound";
		
	}

}

