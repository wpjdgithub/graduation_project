package Grad.Service.wenshu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Grad.Service.nlp.tool.Keywords;

public class Wenshu {
	private String filepath;//文件路径
	private String caseID; //案例号
	private String caseName;
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
	private Set<String> keywords = new HashSet<String>();
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public void setKeywords(Set<String> keywords) {
		this.keywords = keywords;
	}
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
	public String getLawsString(){
		StringBuilder sb = new StringBuilder();
		for(String law:laws){
			sb.append(law+" ");
		}
		String result = sb.toString().trim();
		return result;
	}
	public void setLaws(List<String> laws) {
		this.laws = laws;
	}
	public void setLaws(String laws){
		List<String> list = new ArrayList<String>();
		String[] law = laws.split(" ");
		for(String s:law){
			list.add(s);
		}
		this.laws = list;
	}
	public Map<String, String> getParticipantInfo() {
		return participantInfo;
	}
	public String getParticipantInfoString(){
		StringBuilder sb = new StringBuilder();
		Iterator<String> iterator = this.participantInfo.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			String value = this.participantInfo.get(key);
			sb.append(value+":"+key+" ");
		}
		String result = sb.toString().trim();
		return result;
	}
	public void setParticipantInfo(Map<String, String> participantInfo) {
		this.participantInfo = participantInfo;
	}
	public void setParticipantInfo(String info){
		Map<String,String> map = new HashMap<String,String>();
		String[] pair = info.split(" ");
		for(String p:pair){
			String[] e = p.split(":");
			map.put(e[1], e[0]);
		}
		this.participantInfo = map;
	}
	public Map<String, String> getJudgerInfo() {
		return judgerInfo;
	}
	public String getJudgerInfoString(){
		StringBuilder sb = new StringBuilder();
		Iterator<String> iterator = this.judgerInfo.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			String value = this.judgerInfo.get(key);
			sb.append(value+":"+key+" ");
		}
		String result = sb.toString().trim();
		return result;
	}
	public void setJudgerInfo(Map<String, String> judgerInfo) {
		this.judgerInfo = judgerInfo;
	}
	public void setJudgerInfo(String info){
		Map<String,String> map = new HashMap<String,String>();
		String[] pair = info.split(" ");
		for(String p:pair){
			String[] e = p.split(":");
			map.put(e[1], e[0]);
		}
		this.judgerInfo = map;
	}
	public String getFullText() {
		return fullText;
	}
	public void setFullText(String fullText) {
		this.fullText = fullText;
	}
	public List<String> getSentences(){
		List<String> list = new LinkedList<String>();
		String[] s = this.fullText.split("[,，.。;；\"\\“\\”、《》：:]");
		for(int i = 0;i < s.length;i++)
			list.add(s[i]);
		return list;
	}
	public String getKeywords(){
		StringBuilder sb = new StringBuilder();
		Iterator<String> iterator = this.keywords.iterator();
		while(iterator.hasNext()){
			String keyword = iterator.next();
			sb.append(keyword+" ");
		}
		String result = sb.toString().trim();
		return result;
	}
	public void setKeywords(String keywords){
		Set<String> set = new HashSet<String>();
		String[] keyword = keywords.split(" ");
		for(String word:keyword){
			set.add(word);
		}
		this.keywords = set;
	}
	public void addKeyword(String keyword){
		this.keywords.add(keyword);
	}
}
