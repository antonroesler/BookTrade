package de.frauas.intro.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;

import de.frauas.intro.form.BookSummary;
import de.frauas.intro.model.Book;
import de.frauas.intro.model.User;

@Service
public class UserDAO {
	
	HashMap<String, User> users = new HashMap();
	
	public void addUser(User user) {
		users.put(user.getHash(), user);
	}
	
	public User getUser(String userHash) {
		return users.get(userHash);
	}
	
	public ArrayList<User> getAllUsers() {
		Set<String> hashes = users.keySet();
		ArrayList<User> allUsers = new ArrayList<>();
		for (String hash : hashes) {
			allUsers.add(users.get(hash));
		}
		return allUsers;
	}

	public ArrayList<BookSummary> getBooks(String userHash) {
		for (User user : getAllUsers()) {
			System.out.println("all:" + user.getHash());
		}
		User user = users.get(userHash);
		if (user != null) {
		ArrayList<Book> bookIds = user.getBooks();
		if (bookIds.isEmpty()) {
			Book book = new Book();
			book.setId("B9f567DUudkC");
			bookIds.add(book);
		}
		ArrayList<BookSummary> bookSummaries = GoogleBookAPI.getAllBooksById(bookIds);
		return bookSummaries;
		}
		else return null;
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	FirebaseInitializer initializer = new FirebaseInitializer();
//	Firestore db = initializer.getFirebase();
//	
//	
//	public void addUser(String username, String passowrd) {
//		User user = new User(username, passowrd);
//		ApiFuture<WriteResult> future = db.collection("Users").document(user.getHash()).set(user.toHashMap());
//		
//	}
//	
//	public void addUser(User user) {
//		ApiFuture<WriteResult> future = db.collection("Users").document(user.getHash()).set(user.toHashMap());
//	}
//	
//	public void getUser(String userHash) throws InterruptedException, ExecutionException {
//		DocumentReference documentReference = db.collection("Users").document(userHash);
//		ApiFuture<DocumentSnapshot> future = documentReference.get();
//		DocumentSnapshot document = future.get();
//		
//		if (document.exists()) {
//			System.out.println(document.get("password"));
//		}
//		else {
//			System.out.println("Not FOUND");
//		}
//	}
//
//	public Object getBooks(String userHash) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
