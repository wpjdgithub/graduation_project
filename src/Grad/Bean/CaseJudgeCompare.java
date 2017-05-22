package Grad.Bean;

import java.io.Serializable;

public class CaseJudgeCompare implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4836977992477674446L;
	private String id;
	private String title;
	private int alike;// 1 极其相似        2不完全相似             3较少相似
	private String same_core; //相同的核心词汇
	private String date;
	
	public CaseJudgeCompare(){}
	
	public CaseJudgeCompare(String id, String title, int alike, String same_core, String date) {
		super();
		this.id = id;
		this.title = title;
		this.alike = alike;
		this.same_core = same_core;
		this.date = date;
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
	public int getAlike() {
		return alike;
	}
	public void setAlike(int alike) {
		this.alike = alike;
	}

	public String getSame_core() {
		return same_core;
	}

	public void setSame_core(String same_core) {
		this.same_core = same_core;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	public String toString(){
		return id+";"+title+";"+same_core+";"+date;
	}
	
}
