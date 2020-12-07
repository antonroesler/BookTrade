package de.frauas.intro.model;

public class Book {

	private String title;
	private String ISBN;
	private String authorString;

	public Book(String title, String iSBN, String authorString) {
		super();
		this.title = title;
		ISBN = iSBN;
		this.authorString = authorString;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getAuthorString() {
		return authorString;
	}

	public void setAuthorString(String authorString) {
		this.authorString = authorString;
	}

}
