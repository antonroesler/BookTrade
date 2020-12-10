package de.frauas.intro.form;

import java.net.URI;

public class BookForm {

	private String title;
	private String author;
	private String isbn;
	private URI googleBooksReferenceUri;
	private String bookAbstract;
	private String publisher;

	public URI getGoogleBooksReferenceUri() {
		return googleBooksReferenceUri;
	}

	public void setGoogleBooksReferenceUri(URI googleBooksReferenceUri) {
		this.googleBooksReferenceUri = googleBooksReferenceUri;
	}

	public String getBookAbstract() {
		return bookAbstract;
	}

	public void setBookAbstract(String bookAbstract) {
		this.bookAbstract = bookAbstract;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

}
