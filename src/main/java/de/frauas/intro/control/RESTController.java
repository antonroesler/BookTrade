package de.frauas.intro.control;

import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.frauas.intro.data.UserDatabase;
import de.frauas.intro.model.User;
import de.frauas.intro.model.UserBookCategory;

/**
 * A REST controller for admin use only. Admins can add/delete users, add books
 * to users and more.
 * 
 * @author Anotn Roesler
 *
 */
@RestController
@RequestMapping(value = "/api")
public class RESTController {

	@Autowired
	UserDatabase userDatabase;

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	@ResponseBody
	public String getUser() {

		return "WELCOME";

	}

	@RequestMapping(value = "/user", method = RequestMethod.POST, consumes = { "application/JSON" })
	@ResponseBody
	public ResponseEntity<String> addUser(@RequestBody User user, @RequestHeader("Authorization") String auth) {
		if (isValidAuthentication(auth)) {
			if (userDatabase.addUser(user))
				return new ResponseEntity<>(HttpStatus.CREATED);
			else
				return new ResponseEntity<String>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/user/{username}", method = RequestMethod.GET, produces = { "application/JSON" })
	@ResponseBody
	public ResponseEntity<String> getUser(@PathVariable String username, @RequestHeader("Authorization") String auth) {
		if (isValidAuthentication(auth)) {
			User user = userDatabase.getUser(username);
			if (user != null)
				return new ResponseEntity<String>(user.toJSON(), HttpStatus.OK);
			else
				return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET, produces = { "application/JSON" })
	@ResponseBody
	public ResponseEntity<String> getAllUsers(@RequestHeader("Authorization") String auth) {
		String body = "";
		if (isValidAuthentication(auth)) {
			for (User user : userDatabase.getAllUsers()) {
				body += user.toJSON();
			}
			return new ResponseEntity<String>(body, HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/user/{username}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> deleteUser(@PathVariable String username,
			@RequestHeader("Authorization") String auth) {
		if (isValidAuthentication(auth)) {
			if (userDatabase.deleteUser(username))
				return new ResponseEntity<String>(HttpStatus.OK);
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

	}

	@RequestMapping(value = "/user/{username}/{bookId}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> addBookToUser(@PathVariable String username, @PathVariable String bookId,
			@RequestHeader("Authorization") String auth) {
		if (isValidAuthentication(auth)) {
			if (userDatabase.addBookToUser(username, bookId, UserBookCategory.OWNED))
				return new ResponseEntity<String>(HttpStatus.OK);
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/user/books/{username}", method = RequestMethod.GET, produces = { "application/JSON" })
	@ResponseBody
	public ResponseEntity<String> getAllBooksFromUser(@PathVariable String username,
			@RequestHeader("Authorization") String auth) {
		// TODO: refactor!
		String body = "";
		if (isValidAuthentication(auth)) {
			ArrayList<String> books = userDatabase.getBooksFromUser(username, UserBookCategory.OWNED);
			body = "{\"books_owned\":[";
			for (int i = 0; i < books.size(); i++) {
				String string = books.get(i);
				body += "\"";
				body += string;
				body += "\"";
				if (books.size() > i) {
					body += ",";
				}
			}
			body += "],";
			ArrayList<String> books2 = userDatabase.getBooksFromUser(username, UserBookCategory.WANTED);
			body += "\"books_wanted\":[";
			for (int i = 0; i < books2.size(); i++) {
				String string = books2.get(i);
				body += "\"";
				body += string;
				body += "\"";
				if (books2.size() > i) {
					body += ",";
				}
			}
			body += "]}";
			return new ResponseEntity<>(body, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/user/changeList/{username}/{bookId}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> changeBook(@PathVariable String username, @PathVariable String bookId,
			@RequestHeader("Authorization") String auth) {
		if (isValidAuthentication(auth)) {
			if (userDatabase.changeBook(username, bookId))
				return new ResponseEntity<String>(HttpStatus.OK);
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	private boolean isValidAuthentication(String auth) {
		if (auth.equals("707bde15210b2aba"))
			return true;
		return false;

	}

}
