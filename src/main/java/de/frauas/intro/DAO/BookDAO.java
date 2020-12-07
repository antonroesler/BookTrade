package de.frauas.intro.DAO;

import java.util.ArrayList;
import java.util.List;

import de.frauas.intro.model.Book;

public class BookDAO {
	
	List<Book> books = new ArrayList<>();
	
	public List<Book> getBooks() {
		return books;
	}
	
	public void addBook(Book book) {
		books.add(book);
	}
	
	public void addBook(String title, String iSBN, String authorString) {
		books.add(new Book(title, iSBN, authorString));
	}
}
