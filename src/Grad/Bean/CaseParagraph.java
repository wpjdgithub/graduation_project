package Grad.Bean;

import java.io.Serializable;
import java.util.ArrayList;

public class CaseParagraph implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8077364414994745509L;
	private ArrayList<Sentence> list;

	public ArrayList<Sentence> getList() {
		return list;
	}

	public void setList(ArrayList<Sentence> list) {
		this.list = list;
	}
	
	
}
