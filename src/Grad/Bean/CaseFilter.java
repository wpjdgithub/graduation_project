package Grad.Bean;

import java.io.Serializable;

public class CaseFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5971374877083259623L;
	
	private int id;
	private String name;
	private int num;
	private boolean hasChild;
	
	public CaseFilter(){
		this.id = 0;
		this.name = null;
		this.num = 0;
		this.hasChild = false;
	}

	public CaseFilter(int id, String name, int num, boolean hasChild) {
		super();
		this.id = id;
		this.name = name;
		this.num = num;
		this.hasChild = hasChild;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public void increase(int count){
		this.num = this.num + count;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}
	

}
