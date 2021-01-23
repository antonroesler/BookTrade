package de.frauas.intro.model;

import java.net.URI;
import java.util.HashMap;

import javax.swing.DefaultListCellRenderer.UIResource;

/**
 * VolumeInfo exits due to the structure of the JSON objects returned by the
 * GoogleBooksAPI. A volume info is part of a book object and stores the most
 * relevant information about that book.
 * 
 * @author Anton Roesler
 *
 */
public class VolumeInfo {

	private String title;
	private String subtitle;
	private String[] authors;
	private String publisher;
	private int pageCount;
	private HashMap<String, URI> imageLinks;
	private URI infoLink;

	/**
	 * 
	 * @return Name of the publisher.
	 */
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * A HashMap of image names and URIs to that image. Most needed image are
	 * 'smallThumbnail' and 'thumbnail'.
	 * 
	 * @return The hash map of image names and their URIs.
	 */
	public HashMap<String, URI> getImageLinks() {
		return imageLinks;
	}

	public void setImageLinks(HashMap<String, URI> imageLinks) {
		this.imageLinks = imageLinks;
	}

	/**
	 * Info Link is a URL to the resource at Google Books
	 * 
	 * @return The URI
	 */
	public URI getInfoLink() {
		return infoLink;
	}

	public void setInfoLink(URI infoLink) {
		this.infoLink = infoLink;
	}

	/**
	 * An array containing all the authors.
	 * 
	 * @return An array with the Author names as Strings.
	 */
	public String[] getAuthors() {
		return authors;
	}

	public void setAuthors(String[] authors) {
		this.authors = authors;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

}
