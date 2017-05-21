package Grad.Bean;

import java.io.Serializable;

public class CaseOccur implements Serializable, Comparable<CaseOccur> {
	private String date;
	private int num;
	public CaseOccur(String date, int num) {
		super();
		this.date = date;
		this.num = num;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public int compareTo(CaseOccur o) {
		// TODO Auto-generated method stub
		return this.date.compareTo(o.getDate());
	}
	
}
