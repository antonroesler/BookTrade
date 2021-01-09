package de.frauas.intro.form;

public class UserBookInfoForm {
	
	private String hash;
	private String bookId;

	public UserBookInfoForm() {
	}
	
	public UserBookInfoForm(String userHash) {
		this.hash = userHash;
	}
	
	public UserBookInfoForm(String userHash, String bookid) {
		this.hash = userHash;
		this.bookId = bookid;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}



}
