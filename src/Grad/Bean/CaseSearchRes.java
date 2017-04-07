package Grad.Bean;

import java.io.Serializable;
import java.util.List;

public class CaseSearchRes implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6524093256667980182L;
	private List<CaseBrief> brief;
	private List<CaseFilter> filter;
	
	public CaseSearchRes() {}
	
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
