package de.frauas.intro.model;

import java.awt.Image;
import java.net.URI;

import de.frauas.intro.form.VolumeInfo;

public class Book {
	
	private String id;
    private String kind;
    private VolumeInfo volumeInfo;
    private String autor;
    private String shortTitle;
    private URI imageUri;
    
	public VolumeInfo getVolumeInfo() {
		return volumeInfo;
	}
	public void setVolumeInfo(VolumeInfo volumeInfo) {
		this.volumeInfo = volumeInfo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
    

	public String authors() {
    	String authorString = "";
    	for (String aut : volumeInfo.getAuthors()) {
			authorString += aut;
			authorString += ", ";
		}
    	return authorString;
    }
	
	public void setData() {
		this.autor = volumeInfo.getAuthors()[0];
		String title = volumeInfo.getTitle();
		if (title.length() > 35) {
			this.setShortTitle(volumeInfo.getTitle().substring(0,35));
		}else {
			this.setShortTitle(volumeInfo.getTitle());
		}
		
	}
	
	public void setImgUri() {
		this.imageUri = volumeInfo.getImageLinks().get("thumbnail");
	}
	
	public URI getImageUri() {
		return imageUri;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getShortTitle() {
		return shortTitle;
	}
	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

}
