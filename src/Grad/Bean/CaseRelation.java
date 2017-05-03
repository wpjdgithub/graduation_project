package Grad.Bean;

import java.io.Serializable;

public class CaseRelation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4043564312035979745L;
	private String id;
	private String title;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public CaseRelation(String id, String title) {
		super();
		this.id = id;
		this.title = title;
	}
	
	public CaseRelation(){}
	public String toString(){
		return title;
	}
}
