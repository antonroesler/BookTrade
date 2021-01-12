package de.frauas.intro.model;

import java.net.URI;

/**
 * A POJO that represents a book. It is in the form to be used as a RestTamplete
 * to get information form the GoogleBooksAPI. This is the reason why the book
 * does not have an attribute title etc. Additional attributes that store
 * information about the book are located with in the volumeInfo attribute, due
 * to the structure of the GoogleBooksAPI JSON format.
 * 
 * @author Anton Roesler
 *
 */
public class Book {

	private String id;
	private VolumeInfo volumeInfo;
	private String autor;
	private String shortTitle;
	private URI imageUri;

	/**
	 * Volume info is used to store title, publisher, list of authors and more. This
	 * is due to the structure of the GoogleBooksAPI JSON format.
	 * 
	 * @return an volumeInfo object.
	 */
	public VolumeInfo getVolumeInfo() {
		return volumeInfo;
	}

	public void setVolumeInfo(VolumeInfo volumeInfo) {
		this.volumeInfo = volumeInfo;
	}

	/**
	 * The id is the id given by GoogleBooks.
	 * 
	 * @return the id as a string.
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * This Method is important before using a book inside a thymeleaf template. The
	 * book attribute autor and shortTitle are not included in the GoogleBooks JSON
	 * objects. But they are required by the templates. setData() sets these
	 * attributes to their appropriate values. It also makes sure that the title is
	 * no longer than 35 characters and it only includes the first author.
	 */
	public void setData() {
		if (volumeInfo.getAuthors() != null) {
			if (volumeInfo.getAuthors().length != 0) {
				this.autor = volumeInfo.getAuthors()[0];
			}
		}
		String title = volumeInfo.getTitle();
		if (title.length() > 35) {
			this.setShortTitle(volumeInfo.getTitle().substring(0, 35));
		} else {
			this.setShortTitle(volumeInfo.getTitle());
		}
	}

	/**
	 * The imageURI is an URI for an thumbnail image file of the books cover. Given
	 * by GoogleBooks.
	 */
	public void setImgUri() {
		this.imageUri = volumeInfo.getImageLinks().get("thumbnail");
	}

	/**
	 * The imageURI is an URI for an thumbnail image file of the books cover. Given
	 * by GoogleBooks.
	 * 
	 * @return the URI of the thumbnail image.
	 */
	public URI getImageUri() {
		return imageUri;
	}

	/**
	 * The author is the first author of the book as specified by GoogleBooks.
	 * 
	 * @return the first author of the book.
	 */
	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	/**
	 * The shorttitle is a shortened version of the original title. Or the full title
	 * if short enough. Default max length is 35 characters.
	 * 
	 * @return the short title as a string. 
	 */
	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

}
