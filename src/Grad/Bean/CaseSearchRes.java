package Grad.Bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CaseSearchRes implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6524093256667980182L;
	private List<CaseBrief> brief;
	private List<CaseFilter> filter;
	
	public CaseSearchRes() {
		this.brief = new ArrayList<CaseBrief>();
		this.filter = new ArrayList<CaseFilter>();
	}
	
	public void addCaseBrief(CaseBrief caseBrief){
		this.brief.add(caseBrief);
	}
	
	public void addCaseFilter(CaseFilter caseFilter){
		this.filter.add(caseFilter);
	}
	
	public void addCaseFilter(List<CaseFilter> caseFilters){
		this.filter.addAll(caseFilters);
	}
	
	public CaseSearchRes(List<CaseBrief> brief, List<CaseFilter> filter) {
		super();
		this.brief = brief;
		this.filter = filter;
	}

	public List<CaseBrief> getBrief() {
		return brief;
	}

	public void setBrief(List<CaseBrief> brief) {
		this.brief = brief;
	}

	public List<CaseFilter> getFilter() {
		return filter;
	}

	public void setFilter(List<CaseFilter> filter) {
		this.filter = filter;
	}
}
