package de.frauas.intro.session;

import java.time.LocalTime;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import de.frauas.intro.model.User;

@Service
public class SessionHandler {
	
	HashMap<String, User> activeSession = new HashMap<>();
	
	
	public String addSession(User user) {
		LocalTime time = LocalTime.now();
		String userTime = user.getUsername()+time;
		String sessionHash = Integer.toHexString(userTime.hashCode());
		activeSession.put(sessionHash, user);
		return sessionHash;
	}
	
	public User getUser(String sessionHash) {
		User user = activeSession.get(sessionHash);
		return user;
	}

	public void dropSession(String session) {
		activeSession.remove(session);
		
	}

}
