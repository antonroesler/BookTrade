package de.frauas.intro.DAO;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


import javax.annotation.PostConstruct;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class FirebaseInitializer {

	@PostConstruct // Ones the application is created this will be executed.
	private void initDB() throws IOException {

		FileInputStream serviceAccount = new FileInputStream("./bookcircle-cfa89-firebase-adminsdk-52lk9-3c6e654e06.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

	
		FirebaseApp.initializeApp(options);
		

	}

	public Firestore getFirebase() {
		return FirestoreClient.getFirestore();
	}

}
