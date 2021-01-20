package de.frauas.intro.session;

import java.time.LocalTime;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import de.frauas.intro.model.User;

/**
 * The Session handler is a custom build class to handle multiple user sessions
 * at ones. It is not using cookies but pathvariables.
 * 
 * @author Anton Roesler
 *
 */
@Service
public class SessionHandler {

	/**
	 * A Hash map that stored session ids of active sessions and references to the
	 * user that belongs to a session.
	 */
	HashMap<String, User> activeSession = new HashMap<>();

	/**
	 * When this method is called a new session will be started. The method creates
	 * a new session id (which is a hash value made up by the combination of user
	 * name and time to ensure that it is different for all users every time). The
	 * session id with a reference to the user is stored in the active sessions hash
	 * map.
	 * 
	 * @param user The user object that wants to starts a new session.
	 * @return the newly created session id as a string.
	 */
	public String addSession(User user) {
		LocalTime time = LocalTime.now();
		String userTime = user.getUsername() + time;
		String sessionHash = Integer.toHexString(userTime.hashCode());
		activeSession.put(sessionHash, user);
		return sessionHash;
	}

	/**
	 * Returns a user for a given session id.
	 * 
	 * @param session The session id as a string.
	 * @return the user object that is stored for this session id.
	 */
	public User getUser(String session) {
		User user = activeSession.get(session);
		return user;
	}

	/**
	 * Deletes a session. Used when user logs out.
	 * 
	 * @param session The session id, of the session to delete, as a string.
	 */
	public void dropSession(String session) {
		activeSession.remove(session);

	}

}
