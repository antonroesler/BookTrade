package de.frauas.intro.form;

import de.frauas.intro.model.Book;

/**
 * Used to receive a list of books from the Google Book API. The API returns an
 * json object with an array of book objects.
 * 
 * @author Anton Roesler
 *
 */
public class SearchResultSummary {

	private int totalItems;
	private Book[] items;

	/**
	 * Number of total items returned by Google books API.
	 * 
	 * @return number of total items as int.
	 */
	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public Book[] getItems() {
		return items;
	}

	public void setItems(Book[] items) {
		this.items = items;
	}

	/**
	 * To fill all fields (attributes) of each book, the method book.setData needs
	 * to be called. This f´method does that for every book in the array.
	 */
	public void setData() {
		try {
			for (Book book : items) {
				book.setData();
			}
		} catch (Exception e) {
			System.out.println("No Books found"); // Dirty solution but works in the case books is empty.
		}

	}

}
