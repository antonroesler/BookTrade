package de.frauas.intro.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

public class User {

	private String username;
	private String password;
	private ArrayList<Book> books = new ArrayList<>();
	

	

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getHash() {
		String userString = username + password;
		String hashCode = Integer.toHexString(userString.hashCode());
		hashCode = hashCode.substring(0, 7);
		return hashCode;
	}

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

	public ArrayList<Book> getBooks() {
		return books;
	}

	public void addBook(Book book) {
		this.books.add(book);
	}
	
	public HashMap<String, String> toHashMap() {
		HashMap<String, String> hashMap = new HashMap<>();
		hashMap.put("username", username);
		hashMap.put("password", password);
		return hashMap;
	}

	public String toJSON() {
		return "{ \"username\":\""+username+"\", \"password\":\""+password+"\" , \"hash\":\""+getHash()+"\" }";
		
	}

}