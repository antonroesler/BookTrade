package de.frauas.intro.form;

public class UserBookInfoForm {
	
	private String user;
	private String bookId;

	public UserBookInfoForm() {
	}
	
	public UserBookInfoForm(String userHash) {
		this.user = userHash;
	}
	
	public UserBookInfoForm(String userHash, String bookid) {
		this.user = userHash;
		this.bookId = bookid;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}



}
