package tfidf;
import java.util.List;
public class WenshuWords {
	private String caseID;
	private String caseBrief;
	private List<String> words;
	public WenshuWords(String caseID, String caseBrief, List<String> words) {
		super();
		this.caseID = caseID;
		this.caseBrief = caseBrief;
		this.words = words;
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
	public List<String> getWords() {
		return words;
	}
	public void setWords(List<String> words) {
		this.words = words;
	}
}