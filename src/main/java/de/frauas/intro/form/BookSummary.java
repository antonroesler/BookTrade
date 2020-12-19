package de.frauas.intro.form;

import java.awt.Image;
import java.net.URI;

public class BookSummary {
	
	private String id;
    private String kind;
    private VolumeInfo volumeInfo;
    private String aut;
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
    
    public String getAut() {
		return aut;
	}
	public void setAut(String aut) {
		this.aut = aut;
	}
	public String authors() {
    	String authorString = "";
    	for (String aut : volumeInfo.getAuthors()) {
			authorString += aut;
			authorString += ", ";
		}
    	return authorString;
    }
	
	public void setimageUri() {
		this.imageUri = volumeInfo.getImageLinks().get("thumbnail");
	}
	public URI getImageUri() {
		return imageUri;
	}

}
