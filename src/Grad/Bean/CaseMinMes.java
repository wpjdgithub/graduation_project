package Grad.Bean;

import java.io.Serializable;

public class CaseMinMes implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6994288883752244215L;
	private String title;
	private String uploadDate;
	
	public CaseMinMes(String title, String uploadDate) {
		super();
		this.title = title;
		this.uploadDate = uploadDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	
}
