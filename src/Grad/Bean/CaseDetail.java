package Grad.Bean;

import java.util.ArrayList;

public class CaseDetail {
	private CaseBrief brief;
	private ArrayList<String> context; //内容分为多段 ，每一个段落一个String
	private ArrayList<String> relatedCase;
	private ArrayList<String> relatedLaw;
	
	public CaseBrief getBrief() {
		return brief;
	}
	public void setBrief(CaseBrief brief) {
		this.brief = brief;
	}
	public ArrayList<String> getContext() {
		return context;
	}
	public void setContext(ArrayList<String> context) {
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