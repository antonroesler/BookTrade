package de.frauas.intro.DAO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.util.JSONPObject;

import de.frauas.intro.form.BookSummary;
import de.frauas.intro.form.SearchResultSummary;
import de.frauas.intro.model.Book;

public class GoogleBookAPI {

	public static BookSummary getBookByID(String id) {
		String uri = "https://www.googleapis.com/books/v1/volumes/" + removeLeadingSlash(id);
		RestTemplate restTemplate = new RestTemplate();
		BookSummary bookSummary = restTemplate.getForObject(uri, BookSummary.class);
		return bookSummary;
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

	public static ArrayList<BookSummary> getAllBooksById(List<Book> bookIds) {
		ArrayList<BookSummary> bookSummaries = new ArrayList<>();
		if (bookIds != null) {
			for (Book book : bookIds) {
				bookSummaries.add(getBookByID(book.getId()));
			}
		}
		return bookSummaries;
	}
}
