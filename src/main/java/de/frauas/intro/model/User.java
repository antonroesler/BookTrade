package de.frauas.intro.model;

/**
 * A class to represent users of the application. Every user has an username and
 * a password. A hash value can be calculated for every user. The hash value is
 * used to store users in the database and to refer to a user in the web
 * application.
 * 
 * Users can be stored in the user database.
 * 
 * @author Anton Roesler
 *
 */
public class User {

	private String username;
	private String password;

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	// Getters and Setters
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

	/**
	 * Method to create a user in JSON format. Including: username and password.
	 * 
	 * @return A string in JSON format, with the user's information.
	 */
	public String toJSON() {
		return "{ \"username\":\"" + username + "\", \"password\":\"" + password + "\" }";

	}

}