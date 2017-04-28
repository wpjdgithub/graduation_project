package Grad.Bean;
public class CaseUploadDetail {
	private String username;
	private String caseTitle;
	private String caseContext;
	private String uploadDate;
	public CaseUploadDetail(String username, String caseTitle, String caseContext, String uploadDate) {
		super();
		this.username = username;
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
	public CaseMinMes toCaseMinMes() {
		CaseMinMes mes = new CaseMinMes();
		mes.setId(this.caseTitle);
		mes.setTitle(this.caseTitle);
		mes.setUploadDate(uploadDate);
		return mes;
	}
}