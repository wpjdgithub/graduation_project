package Grad.Bean;
import java.io.Serializable;
import java.util.Date;
import Grad.Factory.Level_court;
import Grad.Factory.Process_judgement;
import Grad.Factory.Type_case;
import Grad.Factory.Type_text;
public class SearchInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 515103577292914262L;
	private String full_text; // 全文
	private String brief; // 案由
	private String name_case; // 案件名称
	private String num; // 案件编号
	private String name_court; // 法院名称
	private int level_court; // 法院层级
	private int type_case; // 案件类型
	private int judegment; // 审判程序
	private int type_text; // 文书类型
	private Date sdate; // 日期
	private Date edate;
	private String judex; // 审判官
	private String litigant; // 当事人
	private String legistative_authority; // 法律依据
	public SearchInfo(){}
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("fulltext:").append("\""+full_text+"\",");
		sb.append("brief:").append("\""+brief+"\",");
		String result = sb.toString();
		return result;
	}
	public String getFull_text() {
		return full_text;
	}
	public void setFull_text(String full_text) {
		this.full_text = full_text;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getName_case() {
		return name_case;
	}
	public void setName_case(String name_case) {
		this.name_case = name_case;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getName_court() {
		return name_court;
	}
	public void setName_court(String name_court) {
		this.name_court = name_court;
	}
	public int getLevel_court() {
		return level_court;
	}
	public String getCourtLevel(){
		String result;
		switch(level_court){
		case 0:
			result = "基层";
			break;
		case 1:
			result = "中级";
			break;
		case 2:
			result = "最高";
			break;
		default:
			result = "No court Level";
		}
		return result;
	}
	public void setLevel_court(int level_court) {
		this.level_court = level_court;
	}
	public int getType_case() {
		return type_case;
	}
	public String getCaseType(){
		String result;
		switch(type_case){
		case 0:
			result = "民事案件";
			break;
		case 1:
			result = "刑事案件";
			break;
		default:
			result = "No Case Type";
		}
		return result;
	}
	public void setType_case(int type_case) {
		this.type_case = type_case;
	}
	public int getJudegment() {
		return judegment;
	}
	public String getCaseProgram(){
		String result;
		switch(judegment){
		case 0:
			result = "一审案件";
			break;
		default:
			result = "No Case Program";
		}
		return result;
	}
	public void setJudegment(int judegment) {
		this.judegment = judegment;
	}
	public int getType_text() {
		return type_text;
	}
	public String getDocumentType(){
		String result;
		switch(type_text){
		case 0:
			result = "裁定书";
			break;
		case 1:
			result = "调解书";
			break;
		case 2:
			result = "判决书";
			break;
		default:
			result = "No Document Type";
		}
		return result;
	}
	public void setType_text(int type_text) {
		this.type_text = type_text;
	}
	public String getJudex() {
		return judex;
	}
	public void setJudex(String judex) {
		this.judex = judex;
	}
	public String getLitigant() {
		return litigant;
	}
	public void setLitigant(String litigant) {
		this.litigant = litigant;
	}
	public String getLegistative_authority() {
		return legistative_authority;
	}
	public void setLegistative_authority(String legistative_authority) {
		this.legistative_authority = legistative_authority;
	}
	public Date getSdate() {
		return sdate;
	}
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	public Date getEdate() {
		return edate;
	}
	public void setEdate(Date edate) {
		this.edate = edate;
	}
}
