package Grad.Bean;

import java.io.Serializable;

public class CaseCompare implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3680114648732536545L;
	private String id;
	private String title;
	private double rate;
	
	public CaseCompare(){}
	
	public CaseCompare(String id, String title, double rate) {
		super();
		this.id = id;
		this.title = title;
		this.rate = rate;
	}

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
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	
}
