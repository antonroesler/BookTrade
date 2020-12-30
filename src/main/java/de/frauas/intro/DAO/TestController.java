package de.frauas.intro.DAO;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	UserDatabase userDatabase;
	
	@RequestMapping("")
	@ResponseBody
	public String test() {
		userDatabase.addBookToUser("d02e6", "1151");
		userDatabase.addBookToUser("d02e6", "3205");
		
		String str = "";
		ArrayList<String> books = userDatabase.getBooksFromUser("d02e6");
		str += books.size();
		for (String string : books) {
			str += string;
			str += " ";
		}
		return str;
	}

}
