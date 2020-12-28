package de.frauas.intro.form;

import java.net.URI;
import java.util.HashMap;

import javax.swing.DefaultListCellRenderer.UIResource;

public class VolumeInfo {

	private String title;
	private String subtitle;
	private String[] authors;
	private String publisher;
	private String publishDate;
	private int pageCount;
	private HashMap<String, URI> imageLinks;
	private URI infoLink;
	

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public HashMap<String, URI> getImageLinks() {
		return imageLinks;
	}

	public void setImageLinks(HashMap<String, URI> imageLinks) {
		this.imageLinks = imageLinks;
	}

	public URI getInfoLink() {
		return infoLink;
	}

	public void setInfoLink(URI infoLink) {
		this.infoLink = infoLink;
	}

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
