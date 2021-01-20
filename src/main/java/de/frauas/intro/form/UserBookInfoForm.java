package de.frauas.intro.form;

public class UserBookInfoForm {

	private String user;
	private String bookId;

	public UserBookInfoForm() {
	}

	public UserBookInfoForm(String userHash) {
		this.user = userHash;
	}

	/**
	 * 
	 * @param userHash A value that identifies a user, usually the session id, which
	 *                 is mapped to a user by the session handler.
	 * @param bookid   The book id used by google books.
	 */
	public UserBookInfoForm(String userHash, String bookid) {
		this.user = userHash;
		this.bookId = bookid;
	}

	/**
	 * The user hash is a value that identifies a user, usually the session id,
	 * which is mapped to a user by the session handler.
	 * 
	 * @return a string to identify a user.
	 */
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
