package de.frauas.intro.form;

/**
 * A form used to transfer user registration and login data from the view layer
 * to the controller. It stores a string for the username and a string for the
 * password.
 * 
 * @author Anton Roesler
 *
 */
public class LoginForm {

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
