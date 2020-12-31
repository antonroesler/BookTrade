package de.frauas.intro.DAO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.springframework.stereotype.Service;

import de.frauas.intro.control.UserBookCategory;
import de.frauas.intro.model.User;

@Service
public class UserDatabase {
	private final static String databasePath = "./src/main/resources/database";
	private final static File databaseFile = new File(databasePath);

	public ArrayList<User> getAllUseres() {
		String[] userHashes = getDatabasefile().list();
		ArrayList<User> users = new ArrayList<>();
		for (String hashString : userHashes) {
			users.add(getUser(hashString));
		}
		return users;
	}

	public void addUser(String userhash, String password, String name) {
		File newUserFile = makeNewFile(databaseFile.getAbsolutePath(), userhash);
		newUserFile.mkdir();
		addFile("pass", password, newUserFile.getAbsolutePath());
		addFile("name", name, newUserFile.getAbsolutePath());
		addFile("owned", newUserFile.getAbsolutePath());
		addFile("wanted", newUserFile.getAbsolutePath());

	}

	public void addUser(User user) {
		addUser(user.getHash(), user.getPassword(), user.getUsername());

	}

	public User getUser(String userHash) {
		String userPath = getUserPath(userHash);
		String username = readFirstLineFromFile(makeNewFile(userPath, "name"));
		String password = readFirstLineFromFile(makeNewFile(userPath, "pass"));
		return new User(username, password);
	}

	public boolean checkUserPassword(String userhash, String password) {
		File passwordFile = makeNewFile(getUserPath(userhash), "pass");
		System.out.println(password);
		System.out.println(readFirstLineFromFile(passwordFile));
		return password.equals(readFirstLineFromFile(passwordFile));

	}

	public void addBookToUser(String userHash, String bookId, UserBookCategory category) {
		File userBooksFile = makeNewFile(getUserPath(userHash), getCategoryString(category));
		writeToFile(userBooksFile, bookId);
	}

	public ArrayList<String> getBooksFromUser(String userHash, UserBookCategory category) {
		File userBooksFile = makeNewFile(getUserPath(userHash), getCategoryString(category));
		return readFromFile(userBooksFile);
	}

	private void addFile(String fileName, String content, String path) {
		try {
			File newFile = makeNewFile(path, fileName);
			newFile.createNewFile();
			writeToFile(newFile, content);
		} catch (IOException e) {
			System.out.println("File already exists.");
		}

	}

	private void addFile(String fileName, String path) {
		try {
			File newFile = makeNewFile(path, fileName);
			newFile.createNewFile();
		} catch (IOException e) {
			System.out.println("File already exists.");
		}

	}

	private void writeToFile(File file, String content) {
		BufferedWriter bw = null;

		try {
			FileWriter writer = new FileWriter(file, true);
			bw = new BufferedWriter(writer);
			bw.write(content);
			bw.newLine();
			bw.flush();
			System.out.println("Wrote to file: " + content);
		} catch (IOException e) {
			System.out.println("File doesnt exist");
		}

	}

	private ArrayList<String> readFromFile(File file) {
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
		return new File(path + "/" + fileName);
	}

	private String getUserPath(String userHash) {
		return databasePath + "/" + userHash;
	}

	public static File getDatabasefile() {
		return databaseFile;
	}

	public String getCategoryString(UserBookCategory category) {
		switch (category) {
		case OWNED:
			return "owned";
		case WANTED:
			return "wanted";

		}
		return null;
	}

	public void changeBook(String hash, String id) {
		UserBookCategory category = findCategory(hash, id);
		addBookToUser(hash, id, reverseCategory(category));
		delteBookFormUserList(hash, id, category);
	}

	private void delteBookFormUserList(String hash, String id, UserBookCategory category) {

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

	private UserBookCategory reverseCategory(UserBookCategory category) {
		if (category == UserBookCategory.OWNED) {
			return UserBookCategory.WANTED;
		}
		return UserBookCategory.OWNED;
	}

	private UserBookCategory findCategory(String hash, String id) {
		if (getBooksFromUser(hash, UserBookCategory.OWNED).contains(id)) {
			return UserBookCategory.OWNED;
		}
		return UserBookCategory.WANTED;
	}

}
