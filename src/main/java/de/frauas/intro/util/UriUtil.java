package de.frauas.intro.util;

public class UriUtil {
	
	public static String addHeader(String headerName, String value) {
		return headerName + "=" + value + "&";
	}
	
	public static String addUserHeader(String value) {
		return addHeader("user", value);
	}

}
