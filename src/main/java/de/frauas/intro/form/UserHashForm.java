package de.frauas.intro.form;

public class UserHashForm {
	
	private String hash;
	private String id;

	public UserHashForm() {
	}
	
	public UserHashForm(String userHash) {
		this.hash = userHash;
	}
	
	public UserHashForm(String userHash, String bookid) {
		this.hash = userHash;
		this.id = bookid;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
