package de.frauas.intro.form;

/**
 * A transfer object usually used to transfer information from and to hidden
 * inputs between the view and controller layer.
 *
 * User could be a username, but usually is a session id that belongs to a user.
 * Objects from this class should not live long. They are only used to transfer
 * information ones. This makes them versatile. Book id doesn't not have to be
 * used, but can be helpful for situation when a book from a specific user is
 * handled. This form allows to transfer user and book information in one form
 * instead of two.
 *
 * If only user information is transfered, the book id stays null.
 *
 * @author Anton Roesler
 *
 */
public class UserBookInfoForm {

	private String user;
	private String bookId;

	public UserBookInfoForm() {
	}

	/**
	 * Set the attribute user. Which usually is a session id that belongs to the
	 * user.
	 *
	 * @param user A string that usually represents the active session id of an
	 *             user.
	 */
	public UserBookInfoForm(String user) {
		this.user = user;
	}

	/**
	 *
	 * @param user A value that identifies a user, usually the session id, which
	 *                 is mapped to a user by the session handler.
	 * @param bookid   The book id used by google books.
	 */
	public UserBookInfoForm(String user, String bookid) {
		this.user = user;
		this.bookId = bookid;
	}

	/**
	 * User ususally stores a session id of tha active user. But i cloud also store a username. Use it however you need it.
	 * which is mapped to a user by the session handler.
	 *
	 * @return a string to identify a user.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Set the attribute user. Which usually is a session id that belongs to the
	 * user.
	 *
	 * @param user A string that usually represents the active session id of an
	 *             user.
	 */
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
