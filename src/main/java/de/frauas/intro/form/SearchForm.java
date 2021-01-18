package de.frauas.intro.form;

import de.frauas.intro.model.SearchType;

/**
 * Transfer object. Used to transfer information from the view layer to the
 * controller. Used on the search page.
 * 
 * @author Anton Roesler
 *
 */
public class SearchForm {

	private String input;
	private SearchType type;
	private String user;

	/**
	 * The search type is an enum that specifies what the user searches. Possible
	 * values are ALL, TITLE, AUTHOR and ISBN.
	 * 
	 * @return the SearchType enum that the user specified.
	 */
	public SearchType getType() {
		return type;
	}

	public void setType(SearchType type) {
		this.type = type;
	}

	/**
	 * The users search text.
	 * @return The string that the user typed into the form.
	 */
	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	/**
	 * The user hash value of the user who is searching.
	 * @return The user hash value as a string.
	 */
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
