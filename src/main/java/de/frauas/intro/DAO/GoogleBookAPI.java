package de.frauas.intro.DAO;

import java.util.ArrayList;

import org.springframework.web.client.RestTemplate;

import de.frauas.intro.form.SearchResultSummary;
import de.frauas.intro.model.Book;

public class GoogleBookAPI {

	public static Book getBookByID(String id) {
		String uri = "https://www.googleapis.com/books/v1/volumes/" + removeLeadingSlash(id);
		RestTemplate restTemplate = new RestTemplate();
		try {
			Book book = restTemplate.getForObject(uri, Book.class);
			return book;
		}
		catch (Exception e) {
	
		}
		return null;
	}

	public static SearchResultSummary query(String query) {
		String uri = "https://www.googleapis.com/books/v1/volumes?q=" + query.replace(" ", "+");
		RestTemplate restTemplate = new RestTemplate();
		SearchResultSummary resultSummary = restTemplate.getForObject(uri, SearchResultSummary.class);
		return resultSummary;
	}

	public static SearchResultSummary queryByTitle(String query) {
		String uri = "https://www.googleapis.com/books/v1/volumes/?q=intitle:" + query.replace(" ", "+");
		RestTemplate restTemplate = new RestTemplate();
		SearchResultSummary resultSummary = restTemplate.getForObject(uri, SearchResultSummary.class);
		return resultSummary;
	}

	public static SearchResultSummary queryByAuthor(String query) {
		String uri = "https://www.googleapis.com/books/v1/volumes/?q=inauthor:" + query.replace(" ", "+");
		RestTemplate restTemplate = new RestTemplate();
		SearchResultSummary resultSummary = restTemplate.getForObject(uri, SearchResultSummary.class);
		return resultSummary;
	}

	public static SearchResultSummary queryByISBN(String query) {
		String uri = "https://www.googleapis.com/books/v1/volumes/?q=isbn:" + query.replace(" ", "+");
		RestTemplate restTemplate = new RestTemplate();
		SearchResultSummary resultSummary = restTemplate.getForObject(uri, SearchResultSummary.class);
		return resultSummary;
	}

	public static String removeLeadingSlash(String uri) {
		if (uri.startsWith("/")) {
			uri = uri.substring(1);
		}
		return uri;
	}

	public static ArrayList<Book> getAllBooksById(ArrayList<String> arrayList) {
		ArrayList<Book> books = new ArrayList<>();
		if (arrayList != null) {
			for (String book : arrayList) {
				Book apibook = getBookByID(book);
				if (apibook!=null)
					books.add(apibook);
			}
		}
		return books;
	}
}
