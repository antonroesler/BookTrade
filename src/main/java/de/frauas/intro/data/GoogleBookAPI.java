package de.frauas.intro.data;

import java.util.ArrayList;

import org.springframework.web.client.RestTemplate;

import de.frauas.intro.form.SearchResultSummary;
import de.frauas.intro.model.Book;

/**
 * The Class is used to retrieve information about books from the google books
 * API. Books can be found by id, title, author or ISBN.
 *
 * @author Anton Roesler
 *
 */
public class GoogleBookAPI {

	/**
	 * Queries the google books API for an specific id. The id is an id given by
	 * google books uniquely for every book. If the id is valid a book object will
	 * be created.
	 *
	 * @param id The id of the book.
	 * @return An book object as defined in the model.Book class
	 */
	public static Book getBookByID(String id) {
		String uri = "https://www.googleapis.com/books/v1/volumes/" + removeLeadingSlash(id);
		RestTemplate restTemplate = new RestTemplate();
		try {
			Book book = restTemplate.getForObject(uri, Book.class);
			return book;
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * Query the google book API by anything (title, author, isbn....). A list of
	 * book objects with the ten best results will be created.
	 *
	 * @param query A String with which the google books api should be queried.
	 * @return SearchResultSummary object. It contains a list of book objects.
	 */
	public static SearchResultSummary query(String query) {
		String uri = "https://www.googleapis.com/books/v1/volumes?q=" + query.replace(" ", "+");
		RestTemplate restTemplate = new RestTemplate();
		SearchResultSummary resultSummary = restTemplate.getForObject(uri, SearchResultSummary.class);
		return resultSummary;
	}

	/**
	 * Query the google book API by title. A list of book objects with the ten best
	 * results will be created.
	 *
	 * @param query A String with which the google books api should be queried.
	 * @return SearchResultSummary object. It contains a list of book objects.
	 */
	public static SearchResultSummary queryByTitle(String query) {
		String uri = "https://www.googleapis.com/books/v1/volumes/?q=intitle:" + query.replace(" ", "+");
		RestTemplate restTemplate = new RestTemplate();
		SearchResultSummary resultSummary = restTemplate.getForObject(uri, SearchResultSummary.class);
		return resultSummary;
	}

	/**
	 * Query the google book API by Author. A list of book objects with the ten best
	 * results will be created.
	 *
	 * @param query A String with which the google books api should be queried.
	 * @return SearchResultSummary object. It contains a list of book objects.
	 */
	public static SearchResultSummary queryByAuthor(String query) {
		String uri = "https://www.googleapis.com/books/v1/volumes/?q=inauthor:" + query.replace(" ", "+");
		RestTemplate restTemplate = new RestTemplate();
		SearchResultSummary resultSummary = restTemplate.getForObject(uri, SearchResultSummary.class);
		return resultSummary;
	}

	/**
	 * Query the google book API by ISBN. A list of book objects with the ten best
	 * results will be created.
	 *
	 * @param query A String with which the google books api should be queried.
	 * @return SearchResultSummary object. It contains a list of book objects.
	 */
	public static SearchResultSummary queryByISBN(String query) {
		String uri = "https://www.googleapis.com/books/v1/volumes/?q=isbn:" + query.replace(" ", "+");
		RestTemplate restTemplate = new RestTemplate();
		SearchResultSummary resultSummary = restTemplate.getForObject(uri, SearchResultSummary.class);
		return resultSummary;
	}

	/**
	 * This method is used to turn a whole list of book ids into a list containing
	 * the respective books as Java objects. Invalid book ids are ignored. The
	 * method queries the google book API for every string and saves the resulting
	 * book objects.
	 *
	 * @param arrayList An ArrayList containing strings of book ids.
	 * @return A list of book objects.
	 */
	public static ArrayList<Book> getAllBooksById(ArrayList<String> arrayList) {
		ArrayList<Book> books = new ArrayList<>();
		if (arrayList != null) {
			for (String book : arrayList) {
				Book apibook = getBookByID(book);
				if (apibook != null)
					books.add(apibook);
			}
		}
		setBooksData(books);
		return books;
	}

	private static void setBooksData(ArrayList<Book> books) {
		for (Book book : books) {
			try {
				book.setData();
			} catch (Exception e) {
				System.err.println("Error while setting book data: " + book);
			}

		}
	}

	/**
	 * An util method to remove a leading slash from a given string. /word -> word.
	 * But only if the '/' is the first character.
	 *
	 * @param uri A string.
	 * @return the given string without the '/' at the beginning.
	 */
	public static String removeLeadingSlash(String uri) {
		if (uri.startsWith("/")) {
			uri = uri.substring(1);
		}
		return uri;
	}
}
