package de.frauas.intro.DAO;

import java.net.URI;
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
	
	public void addBookFull(String title, String iSBN, String authorString , URI googleBooksReferenceUri, String bookAbstract,
			String publisher) {
		books.add(new Book(title, iSBN, authorString, googleBooksReferenceUri, bookAbstract, publisher));
	}
	
	public void createSomeNewBooks() {
		this.addBook("The Art of Statistics", "987-654-123", "David Spiegelhalter");
		this.addBook("The Book of Why", "987-123-456", "Judea Pearl");
		this.addBook("Clean Code", "987-123-125", "Robert C. Martin");
		this.addBook("A long Titled Book with a Datavisualizationkit", "987-123-000", "Anton Rösler");
	}
}
