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

import de.frauas.intro.DAO.UserDatabase;
import de.frauas.intro.model.User;
import de.frauas.intro.model.UserBookCategory;

@RestController
@RequestMapping(value = "/api/v1")
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

	@RequestMapping(value = "/user/{userHash}", method = RequestMethod.GET, produces = { "application/JSON" })
	@ResponseBody
	public ResponseEntity<String> getUser(@PathVariable String userHash, @RequestHeader("Authorization") String auth) {
		if (isValidAuthentication(auth)) {
			User user = userDatabase.getUser(userHash);
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
			for (User user : userDatabase.getAllUseres()) {
				body += user.toJSON();
			}
			return new ResponseEntity<String>(body, HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/user/{userHash}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> deleteUser(@PathVariable String userHash,
			@RequestHeader("Authorization") String auth) {
		if (isValidAuthentication(auth)) {
			if (userDatabase.deleteUser(userHash))
				return new ResponseEntity<String>(HttpStatus.OK);
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);

	}

	@RequestMapping(value = "/user/{userHash}/{bookId}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> addBookToUser(@PathVariable String userHash, @PathVariable String bookId,
			@RequestHeader("Authorization") String auth) {
		if (isValidAuthentication(auth)) {
			if (userDatabase.addBookToUser(userHash, bookId, UserBookCategory.OWNED))
				return new ResponseEntity<String>(HttpStatus.OK);
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/user/books/{userHash}", method = RequestMethod.GET, produces = { "application/JSON" })
	@ResponseBody
	public ResponseEntity<String> getAllBooksFromUser(@PathVariable String userHash, @RequestHeader("Authorization") String auth) {
		// TODO: refactor!
		String body = "";
		if (isValidAuthentication(auth)) {
			ArrayList<String> books = userDatabase.getBooksFromUser(userHash, UserBookCategory.OWNED);
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
			{

			}
			body += "],";
			ArrayList<String> books2 = userDatabase.getBooksFromUser(userHash, UserBookCategory.WANTED);
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
			{

			}
			body += "]}";
			return new ResponseEntity<>(body, HttpStatus.OK);
		} 
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(value = "/user/changeList/{userHash}/{bookId}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> changeBook(@PathVariable String userHash, @PathVariable String bookId,
			@RequestHeader("Authorization") String auth) {
		if (isValidAuthentication(auth)) {
			if (userDatabase.changeBook(userHash, bookId))
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
