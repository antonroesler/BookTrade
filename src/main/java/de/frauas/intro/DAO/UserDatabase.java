package de.frauas.intro.DAO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.springframework.stereotype.Service;

import de.frauas.intro.model.User;
import de.frauas.intro.model.UserBookCategory;

/**
 * The UserDatabase stores Users in a 'database' directory. A User is saved in a
 * directory named after the Users hash value. For every user there are the
 * following files: - pass (stores the password in a single line) - name (sores
 * the username in a single line) - owned (stores a list of Book IDs, every ID
 * in a new line) - wanted (stores a list of Book IDs, every ID in a new line)
 *
 * @author Anton Roesler
 */
@Service
public class UserDatabase {
	private final static String databasePath = "./src/main/resources/database";
	private final static File databaseFile = new File(databasePath);

	/**
	 * Searches the database for all users.
	 * 
	 * @return ArrayList wit all users as'User' objects.
	 */
	public ArrayList<User> getAllUseres() {
		String[] userHashes = listAllUserHashes();
		ArrayList<User> users = new ArrayList<>();
		for (String hashString : userHashes) {
			users.add(getUser(hashString));
		}
		return users;
	}

	/**
	 * Adds a new users to the database. Only if username is not already taken.
	 * 
	 * @param userhash the users hash value
	 * @param password the users password
	 * @param name     the username
	 */
	public boolean addUser(String userhash, String password, String name) {
		if (!userNameTaken(name)) {
			File newUserFile = makeNewFile(databaseFile.getAbsolutePath(), userhash);
			newUserFile.mkdir();
			addFile("pass", password, newUserFile.getAbsolutePath());
			addFile("name", name, newUserFile.getAbsolutePath());
			addFile("owned", newUserFile.getAbsolutePath());
			addFile("wanted", newUserFile.getAbsolutePath());
			return true;
		}
		return false;
	}

	/**
	 * Adds a new users to the database. Only if username is not already taken.
	 * 
	 * @param user User Object
	 */
	public boolean addUser(User user) {
		return addUser(user.getHash(), user.getPassword(), user.getUsername());

	}

	public boolean deleteUser(String userHash) {
		if (userExists(userHash)) {
			File userfile = makeNewFile(databasePath, userHash);
			for (String file : userfile.list()) {
				makeNewFile(userfile.getAbsolutePath(), file).delete();
			}
			userfile.delete();
			return true;
		}
		return false;
	}

	private boolean userNameTaken(String name) {
		// Checks if a user name is used by any user in the DB
		for (User user : getAllUseres()) {
			if (name.equals(user.getUsername()))
				return true;
		}
		return false;
	}

	/**
	 * Finds all users in the DB that own a given book
	 * 
	 * @param bookId GoogleBookAPI book ID
	 * @return ArrayList of Users
	 */
	public ArrayList<User> getUsersWithBook(String bookId) {
		ArrayList<User> users = getAllUseres();
		ArrayList<User> outputArrayList = new ArrayList<>();
		for (User user : users) {
			if (hasBook(user, bookId)) {
				outputArrayList.add(user);
			}
		}

		return outputArrayList;
	}

	/**
	 * Finds a user by its hash value.
	 * 
	 * @param userHash the hash value
	 * @return User object
	 */
	public User getUser(String userHash) {
		if (userExists(userHash)) {
			String userPath = getUserPath(userHash);
			String username = readFirstLineFromFile(makeNewFile(userPath, "name"));
			String password = readFirstLineFromFile(makeNewFile(userPath, "pass"));
			return new User(username, password);
		}
		return null;
	}

	/**
	 * Checks a given password for a given user.
	 * 
	 * @param userhash the hash value of the user to be checked.
	 * @param password the password to be checked.
	 * @return boolean
	 */
	public boolean checkUserPassword(String userhash, String password) {
		File passwordFile = makeNewFile(getUserPath(userhash), "pass");
		System.out.println(password);
		System.out.println(readFirstLineFromFile(passwordFile));
		return password.equals(readFirstLineFromFile(passwordFile));

	}

	/**
	 * Adds a given book to a given user.
	 * 
	 * @param userHash the hash value of the user
	 * @param bookId   the GoogleBooks ID of the book
	 * @param category wanted/owned by user
	 * @return
	 */
	public boolean addBookToUser(String userHash, String bookId, UserBookCategory category) {
		if (userExists(userHash)) {
			File userBooksFile = makeNewFile(getUserPath(userHash), getCategoryString(category));
			writeToFile(userBooksFile, bookId);
			return true;
		}
		return false;
	}

	/**
	 * Get all books (either from wanted or owned list) form a given user.
	 * 
	 * @param userHash the hash value of the user
	 * @param category from wanted or owned list
	 * @return ArrayList with GoogleBook ID Strings
	 */
	public ArrayList<String> getBooksFromUser(String userHash, UserBookCategory category) {
		File userBooksFile = makeNewFile(getUserPath(userHash), getCategoryString(category));
		return readFromFile(userBooksFile);
	}

	/**
	 * Switches a book from one list of a user to the other.
	 * 
	 * @param hash the user hash value
	 * @param id   the book id
	 * @return
	 */
	public boolean changeBook(String hash, String id) {
		UserBookCategory category = findCategory(hash, id);
		if (userExists(hash)) {
			if (hasBook(getUser(hash), id) || wantsBook(getUser(hash), id)) {
				addBookToUser(hash, id, reverseCategory(category));
				delteBookFormUserList(hash, id, category);
				return true;
			}
		}
		return false;
	}

	/**
	 * Deletes a book from a users list.
	 * 
	 * @param hash:     the user hash value
	 * @param id:       the book id
	 * @param category: the list
	 */
	public void delteBookFormUserList(String hash, String id, UserBookCategory category) {
		File userBooksFile = makeNewFile(getUserPath(hash), getCategoryString(category));
		addFile(getCategoryString(category), getUserPath(hash));
		ArrayList<String> result = readFromFile(userBooksFile);
		userBooksFile.delete();
		File newFile = makeNewFile(getUserPath(hash), getCategoryString(category));
		result.remove(id);
		for (String string : result) {
			writeToFile(userBooksFile, string);
		}
	}

	private boolean hasBook(User user, String bookId) {
		// Checks if given user owns given book.
		if (getBooksFromUser(user.getHash(), UserBookCategory.OWNED).contains(bookId))
			return true;
		return false;
	}

	private boolean wantsBook(User user, String bookId) {
		// Checks if given user wants given book.
		if (getBooksFromUser(user.getHash(), UserBookCategory.WANTED).contains(bookId))
			return true;
		return false;
	}

	private void addFile(String fileName, String content, String path) {
		// Creates and saves a new file with given name and content to a given path.
		try {
			File newFile = makeNewFile(path, fileName);
			newFile.createNewFile();
			writeToFile(newFile, content);
		} catch (IOException e) {
			System.out.println("File already exists.");
		}
	}

	private void addFile(String fileName, String path) {
		// Creates and saves a new file with given name to a given path.
		try {
			File newFile = makeNewFile(path, fileName);
			newFile.createNewFile();
		} catch (IOException e) {
			System.out.println("File already exists.");
		}
	}

	private void writeToFile(File file, String content) {
		// Adds content to a file.
		BufferedWriter bw = null;

		try {
			FileWriter writer = new FileWriter(file, true);
			bw = new BufferedWriter(writer);
			bw.write(content);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			System.out.println("File doen't exist.");
		}

	}

	private ArrayList<String> readFromFile(File file) {
		// Reads content from a file.
		Scanner scanner;
		ArrayList<String> content = new ArrayList<>();
		try {
			scanner = new Scanner(file);
			while (scanner.hasNext()) {
				content.add(scanner.next());

			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("File doens't exists.");
		}
		return content;
	}

	private String readFirstLineFromFile(File file) {
		// Reads the first line from a file.
		Scanner scanner;
		String content = "";
		try {
			scanner = new Scanner(file);
			content += scanner.next();
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("File doens't exists.");
		}
		return content;
	}

	private File makeNewFile(String path, String fileName) {
		// Returns a new file.
		return new File(path + "/" + fileName);
	}

	private String getUserPath(String userHash) {
		// Creates a new user directory in the database.
		return databasePath + "/" + userHash;
	}

	public static File getDatabasefile() {
		return databaseFile;
	}

	public String getCategoryString(UserBookCategory category) {
		// Turns the UserBookCategory enum in the according string.
		switch (category) {
		case OWNED:
			return "owned";
		case WANTED:
			return "wanted";
		}
		return null;
	}

	private boolean userExists(String userHash) {
		// true if a user exists.
		for (String currentUser : listAllUserHashes()) {
			if (currentUser.equals(userHash)) {
				return true;
			}
		}
		return false;
	}

	private String[] listAllUserHashes() {
		return getDatabasefile().list();
	}

	private UserBookCategory reverseCategory(UserBookCategory category) {
		// Changes a given UserBookCategory in the respective other one.
		if (category == UserBookCategory.OWNED) {
			return UserBookCategory.WANTED;
		}
		return UserBookCategory.OWNED;
	}

	private UserBookCategory findCategory(String hash, String id) {
		// returns the category of list in which a book is saved.
		if (getBooksFromUser(hash, UserBookCategory.OWNED).contains(id)) {
			return UserBookCategory.OWNED;
		}
		return UserBookCategory.WANTED;
	}

}
