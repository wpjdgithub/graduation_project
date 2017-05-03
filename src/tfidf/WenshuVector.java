package tfidf;

import java.util.Map;

public class WenshuVector {
	private String caseID;
	private String caseBrief;
	private Map<String,Double> vector;
	public WenshuVector(String caseID, String caseBrief, Map<String, Double> vector) {
		super();
		this.caseID = caseID;
		this.caseBrief = caseBrief;
		this.vector = vector;
	}
	public String getCaseID() {
		return caseID;
	}
	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}
	public String getCaseBrief() {
		return caseBrief;
	}
	public void setCaseBrief(String caseBrief) {
		this.caseBrief = caseBrief;
	}
	public Map<String, Double> getVector() {
		return vector;
	}
	public void setVector(Map<String, Double> vector) {
		this.vector = vector;
	}
	
}