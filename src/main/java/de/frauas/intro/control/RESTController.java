package de.frauas.intro.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.frauas.intro.DAO.UserDatabase;
import de.frauas.intro.model.User;

@RestController
@RequestMapping(value = "/api/v1")
public class RESTController {

	@Autowired
	UserDatabase userDatabase;

	private final String noAuthMsg = "You are not authenticated";

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	@ResponseBody
	public String getUser() {

		return "WELCOME";

	}

	@RequestMapping(value = "/user", method = RequestMethod.POST, consumes = { "application/JSON" })
	@ResponseBody
	public String addUser(@RequestBody User user, @RequestHeader("Authorization") String auth) {
		String out = "";
		if (isValidAuthentication(auth)) {
			if (userDatabase.addUser(user))
				out = "User: " + user.getHash() + " created.";
			else
				out = "Username is already taken.";

		} else
			out = noAuthMsg;
		return out;

	}

	@RequestMapping(value = "/user/{userHash}", method = RequestMethod.GET, produces = { "application/JSON" })
	@ResponseBody
	public String getUser(@PathVariable String userHash, @RequestHeader("Authorization") String auth) {
		String out = "";
		if (isValidAuthentication(auth)) {
			User user = userDatabase.getUser(userHash);
			out += user.toJSON();
		} else
			out = noAuthMsg;
		return out;

	}

	@RequestMapping(value = "/user", method = RequestMethod.GET, produces = { "application/JSON" })
	@ResponseBody
	public String getAllUsers(@RequestHeader("Authorization") String auth) {
		String out = "";
		if (isValidAuthentication(auth)) {
			for (User user : userDatabase.getAllUseres()) {
				out += user.toJSON();
			}

		} else
			out = noAuthMsg;
		return out;

	}

	@RequestMapping(value = "/user/{userHash}", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteUser(@PathVariable String userHash, @RequestHeader("Authorization") String auth) {
		String out = "";
		if (isValidAuthentication(auth)) {
			userDatabase.deleteUser(userHash);
		} else
			out = noAuthMsg;
		return out;

	}
	
	@RequestMapping(value = "/user/{userHash}/{bookId}", method = RequestMethod.PUT)
	@ResponseBody
	public String addBookToUser(@PathVariable String userHash, @PathVariable String bookId, @RequestHeader("Authorization") String auth) {
		String out = "";
		if (isValidAuthentication(auth)) {
			userDatabase.addBookToUser(userHash, bookId, UserBookCategory.OWNED);
			out = "Book added.";
		} else
			out = noAuthMsg;
		return out;
	}
	
	@RequestMapping(value = "/user/books/{userHash}", method = RequestMethod.GET, produces = { "application/JSON" })
	@ResponseBody
	public String getAllBooksFromUser(@PathVariable String userHash, @RequestHeader("Authorization") String auth) {
		String out = "";
		if (isValidAuthentication(auth)) {
			ArrayList<String> books = userDatabase.getBooksFromUser(userHash, UserBookCategory.OWNED);
			out = "{\"books_owned\":[";
			for (int i = 0; i<books.size(); i++) {
				String string = books.get(i);
				out += "\"";
				out += string;
				out += "\"";
				if (books.size() > i) {
					out+= ",";
				}
				
			} {
				
			}
			out += "],";
			ArrayList<String> books2 = userDatabase.getBooksFromUser(userHash, UserBookCategory.WANTED);
			out += "\"books_wanted\":[";
			for (int i = 0; i<books2.size(); i++) {
				String string = books2.get(i);
				out += "\"";
				out += string;
				out += "\"";
				if (books2.size() > i) {
					out+= ",";
				}
				
			} {
				
			}
			out += "]}";
		} else
			out = noAuthMsg;
		return out;
	}

	@RequestMapping(value = "/user/changeList/{userHash}/{bookId}", method = RequestMethod.PUT)
	@ResponseBody
	public String changeBook(@PathVariable String userHash, @PathVariable String bookId, @RequestHeader("Authorization") String auth) {
		String out = "";
		if (isValidAuthentication(auth)) {
			userDatabase.changeBook(userHash, bookId);
			out = "Book changed.";
		} else
			out = noAuthMsg;
		return out;
	}
	
	
	
	private boolean isValidAuthentication(String auth) {
		if (auth.equals("abc"))
			return true;
		return false;

	}

}
