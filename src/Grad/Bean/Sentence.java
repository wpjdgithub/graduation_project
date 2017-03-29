package Grad.Bean;

import java.io.Serializable;

public class Sentence implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8223233057391523490L;
	private String value;
	private boolean needExplain;
	private String explanation;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isNeedExplain() {
		return needExplain;
	}
	public void setNeedExplain(boolean needExplain) {
		this.needExplain = needExplain;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	
	
}
