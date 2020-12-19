package de.frauas.intro.DAO;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.util.JSONPObject;

import de.frauas.intro.form.BookSummary;

public class GoogleBookAPI {

	public static BookSummary getBookByID(String id) {
		String uri = "https://www.googleapis.com/books/v1/volumes/" + removeLeadingSlash(id);
		RestTemplate restTemplate = new RestTemplate();
		BookSummary bookSummary = restTemplate.getForObject(uri, BookSummary.class);
//		System.out.println(bookSummary.getId());
//		System.out.println(bookSummary.getKind());
//		System.out.println(bookSummary.getVolumeInfo().getTitle());
		return bookSummary;
	}
	
	public static String query(String query) {
		String uri = "https://www.googleapis.com/books/v1/volumes?q=" + query.replace(" ", "+");
		RestTemplate restTemplate = new RestTemplate();
		
		return "";
	}

	
	public static String removeLeadingSlash(String uri) {
		if (uri.startsWith("/")) {
			uri = uri.substring(1);
		}
		return uri;
	}
}
