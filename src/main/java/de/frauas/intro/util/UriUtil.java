package de.frauas.intro.util;

/**
 * A helper class to quickly add query parameters to a URL.
 * 
 * @author Anton Roesler
 *
 */
public class UriUtil {

	/**
	 * Creates a string that can be attached to a URI to append a query parameter.
	 * The ? is not included.
	 * 
	 * @param headerName The parameters name.
	 * @param value      The parameters value.
	 * @return A formatted string that can be attached to the and of an existing
	 *         URI.
	 */
	public static String addHeader(String headerName, String value) {
		return headerName + "=" + value + "&";
	}

	/**
	 * Add user parameter to URI.
	 * 
	 * @param value Usually the session id of the active session from that user.
	 * @return A formatted string that can be attached to the and of an existing
	 *         URI.
	 */
	public static String addUserHeader(String value) {
		return addHeader("user", value);
	}

}
