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

	/**
	 * Calculates a user specific hash value to simulate security.
	 * 
	 * The hash value depends on username and password. It's length is set to 7. The
	 * length can be changed but only if all users in the database are deleted as
	 * well. If the length is changed the existing users wont be recognized.
	 * 
	 * @return The calculated hash value as a string.
	 */
	public String getHash() {
		String userString = username + password;
		String hashCode = Integer.toHexString(userString.hashCode());
		hashCode = hashCode.substring(0, 7);
		return hashCode;
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

	// I think i dont need this. 
//	public HashMap<String, String> toHashMap() {
//		HashMap<String, String> hashMap = new HashMap<>();
//		hashMap.put("username", username);
//		hashMap.put("password", password);
//		return hashMap;
//	}

	/**
	 * Method to create a user in JSON format. Including: username, password and the
	 * hash value.
	 * 
	 * @return A string in JSON format, with the user's information.
	 */
	public String toJSON() {
		return "{ \"username\":\"" + username + "\", \"password\":\"" + password + "\" , \"hash\":\"" + getHash()
				+ "\" }";

	}

}