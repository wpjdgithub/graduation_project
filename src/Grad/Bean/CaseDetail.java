package Grad.Bean;

import java.io.Serializable;
import java.util.ArrayList;

public class CaseDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4688749351710425283L;
	private CaseBrief brief;
	private ArrayList<CaseParagraph> context; //内容分为多段 ，每一个段落一个String
	private ArrayList<String> relatedCase;
	private ArrayList<String> relatedLaw;
	
	public CaseBrief getBrief() {
		return brief;
	}
	public void setBrief(CaseBrief brief) {
		this.brief = brief;
	}
	public ArrayList<CaseParagraph> getContext() {
		return context;
	}
	public void setContext(ArrayList<CaseParagraph> context) {
		this.context = context;
	}
	public ArrayList<String> getRelatedCase() {
		return relatedCase;
	}
	public void setRelatedCase(ArrayList<String> relatedCase) {
		this.relatedCase = relatedCase;
	}
	public ArrayList<String> getRelatedLaw() {
		return relatedLaw;
	}
	public void setRelatedLaw(ArrayList<String> relatedLaw) {
		this.relatedLaw = relatedLaw;
	}
	
	
}
