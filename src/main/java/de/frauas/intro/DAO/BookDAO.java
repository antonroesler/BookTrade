package de.frauas.intro.DAO;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.frauas.intro.model.Book;
import de.frauas.intro.model.UserBookCategory;


public class BookDAO {
	

	UserDatabase userDatabase = new UserDatabase();
	
	private void setBooksData(ArrayList<Book> ownedBooks) {
		for (Book book : ownedBooks) {
			book.setData();
		}
	}

	public ArrayList<Book> getBooksFromUser(String userHash, UserBookCategory category) {
		ArrayList<String> bookids = userDatabase.getBooksFromUser(userHash, category);
		ArrayList<Book> ownedBooks = new ArrayList<>();
		if (!bookids.isEmpty()) {
			ownedBooks = GoogleBookAPI.getAllBooksById(bookids);
		}
		try {
			setBooksData(ownedBooks);
		} catch (Exception e) {
			System.out.println("Error while setting Data.");
		}
		return ownedBooks;
	}
	
	
	
	
}
