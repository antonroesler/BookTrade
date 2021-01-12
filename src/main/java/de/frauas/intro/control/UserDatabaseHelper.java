package de.frauas.intro.control;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import de.frauas.intro.DAO.GoogleBookAPI;
import de.frauas.intro.DAO.UserDatabase;
import de.frauas.intro.model.Book;
import de.frauas.intro.model.UserBookCategory;

public class UserDatabaseHelper {
	
	@Autowired
	public UserDatabase userDatabase;
	
	private void setBooksData(ArrayList<Book> ownedBooks) {
		for (Book book : ownedBooks) {
			book.setData();
		}
	}

	private ArrayList<Book> getBooksFromUser(String userHash, UserBookCategory category) {
		ArrayList<String> bookids = userDatabase.getBooksFromUser(userHash, category);
		ArrayList<Book> ownedBooks = new ArrayList<>();
		if (!bookids.isEmpty()) {
			ownedBooks = GoogleBookAPI.getAllBooksById(bookids);
		}
		return ownedBooks;
	}

	
}