package de.frauas.intro.form;

public class SearchResultSummary {
	
	private int totalItems;
	private BookSummary[] items;
	
	public int getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
	public BookSummary[] getItems() {
		return items;
	}
	public void setItems(BookSummary[] items) {
		this.items = items;
	}

	
	

}
