package de.frauas.intro.form;

import de.frauas.intro.model.Book;

public class SearchResultSummary {

	private int totalItems;
	private Book[] items;

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

	public void setData() {
		try {
			for (Book book : items) {
				book.setData();
			}
		} catch (Exception e) {
			System.out.println("No Books found");		}

	}

}
