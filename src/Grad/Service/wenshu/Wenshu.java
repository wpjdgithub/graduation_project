package Grad.Service.wenshu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Grad.Service.nlp.tool.Keywords;

public class Wenshu {
	private String caseID; //案例号
	private String courtName;//经办法院名称
	private String courtLevel;//经办法院级别
	private String courtArea;//行政区划
	private String documentType;//文书类型
	private String documentName;//文书名称
	private String caseYear;//案例年份
	private String caseType;//案例类型
	private String caseProgram;//裁判程序
	private String caseBrief;//案由
	private List<String> laws;//涉及法律，法律名称条目+：+法条内容
	private Map<String,String> participantInfo;//参与人基本信息
	private Map<String,String> judgerInfo;//审判人员信息
	private String fullText;//全文
	private Set<String> keywords;
	public String getCaseID() {
		return caseID;
	}
	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}
	public String getCourtName() {
		return courtName;
	}
	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}
	public String getCourtLevel() {
		return courtLevel;
	}
	public void setCourtLevel(String courtLevel) {
		this.courtLevel = courtLevel;
	}
	public String getCourtArea() {
		return courtArea;
	}
	public void setCourtArea(String courtArea) {
		this.courtArea = courtArea;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getCaseYear() {
		return caseYear;
	}
	public void setCaseYear(String caseYear) {
		this.caseYear = caseYear;
	}
	public String getCaseType() {
		return caseType;
	}
	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}
	public String getCaseProgram() {
		return caseProgram;
	}
	public void setCaseProgram(String caseProgram) {
		this.caseProgram = caseProgram;
	}
	public String getCaseBrief() {
		return caseBrief;
	}
	public void setCaseBrief(String caseBrief) {
		this.caseBrief = caseBrief;
	}
	public List<String> getLaws() {
		return laws;
	}
	public void setLaws(List<String> laws) {
		this.laws = laws;
	}
	public Map<String, String> getParticipantInfo() {
		return participantInfo;
	}
	public void setParticipantInfo(Map<String, String> participantInfo) {
		this.participantInfo = participantInfo;
	}
	public Map<String, String> getJudgerInfo() {
		return judgerInfo;
	}
	public void setJudgerInfo(Map<String, String> judgerInfo) {
		this.judgerInfo = judgerInfo;
	}
	public String getFullText() {
		return fullText;
	}
	public void setFullText(String fullText) {
		this.fullText = fullText;
		this.keywords = new HashSet<String>();
		Iterator<String> iterator = Keywords.getKeywordsIterator();
		while(iterator.hasNext()){
			String keyword = iterator.next();
			if(fullText.contains(keyword))
				this.keywords.add(keyword);
		}
	}
	public List<String> getSentences(){
		List<String> list = new LinkedList<String>();
		String[] s = this.fullText.split("[,，.。;；\"\\“\\”、《》：:]");
		for(int i = 0;i < s.length;i++)
			list.add(s[i]);
		return list;
	}
}
