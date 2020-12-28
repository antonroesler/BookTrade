package de.frauas.intro.form;

import de.frauas.intro.model.SearchType;

public class SearchForm {

	private String input;
	private SearchType type;
	private String user;

	public SearchType getType() {
		return type;
	}

	public void setType(SearchType type) {
		this.type = type;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
