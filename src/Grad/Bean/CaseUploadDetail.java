package Grad.Bean;
public class CaseUploadDetail {
	private String username;
	private int count;
	private String path;
	private String caseTitle;
	private String caseContext;
	private String uploadDate;
	public CaseUploadDetail(String username, int count, String path, String caseTitle, String caseContext,
			String uploadDate) {
		super();
		this.username = username;
		this.count = count;
		this.path = path;
		this.caseTitle = caseTitle;
		this.caseContext = caseContext;
		this.uploadDate = uploadDate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getCaseTitle() {
		return caseTitle;
	}
	public void setCaseTitle(String caseTitle) {
		this.caseTitle = caseTitle;
	}
	public String getCaseContext() {
		return caseContext;
	}
	public void setCaseContext(String caseContext) {
		this.caseContext = caseContext;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public CaseMinMes toCaseMinMes(){
		CaseMinMes result = new CaseMinMes(this.caseTitle,this.uploadDate);
		return result;
	}
}