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
	private ArrayList<CaseRelation> relatedCase;
	private ArrayList<CaseRelation> relatedLaw;
	
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
	public ArrayList<CaseRelation> getRelatedCase() {
		return relatedCase;
	}
	public void setRelatedCase(ArrayList<CaseRelation> relatedCase) {
		this.relatedCase = relatedCase;
	}
	public ArrayList<CaseRelation> getRelatedLaw() {
		return relatedLaw;
	}
	public void setRelatedLaw(ArrayList<CaseRelation> relatedLaw) {
		this.relatedLaw = relatedLaw;
	}
	
	
}
