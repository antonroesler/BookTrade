package de.frauas.intro.model;


public class Inquiry {

	private static long serialVersionUID = 1L;

	private long id;
	private User requestingUser;
	private User requestedUser;
	private Book book;
	

	public Inquiry() {
		this.setId(serialVersionUID ++);
	}

	public Inquiry(User requestingUser, User requestedUser, Book book) {
		super();
		this.requestingUser = requestingUser;
		this.requestedUser = requestedUser;
		this.book = book;
		this.setId(serialVersionUID ++);
	}

	public User getRequestingUser() {
		return requestingUser;
	}

	public void setRequestingUser(User requestingUser) {
		this.requestingUser = requestingUser;
	}

	public User getRequestedUser() {
		return requestedUser;
	}

	public void setRequestedUser(User requestedUser) {
		this.requestedUser = requestedUser;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
