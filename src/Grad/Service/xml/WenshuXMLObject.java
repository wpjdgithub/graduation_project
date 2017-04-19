package Grad.Service.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Grad.Service.wenshu.Wenshu;

public class WenshuXMLObject {
	private String filepath;
	private Document document;
	private Element rootElement;
	public WenshuXMLObject(String filepath){
		this.filepath = filepath;
		DOMParser parser = new DOMParser();
		this.document = parser.parse(filepath);
		this.rootElement = this.document.getDocumentElement();
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public Element getRoot(){
		return this.rootElement;
	}
	/*
	 * This function is to get the full text of the document.
	 */
	public String getFullText(){
		String fullContent = this.rootElement.getAttribute("value");
		return fullContent;
	}
	/*
	 * This function is to get the beginning of the document.
	 */
	public String getDocumentBeginning(){
		Element element = getWSElement();
		String documentBeginning = element.getAttribute("value");
		return documentBeginning;
	}
	/*
	 * This function is called to get the name of the court handling this case.
	 */
	public String getHandlingCourt(){
		Element wsElement = getWSElement();
		Element jbfyElement = (Element)wsElement.getElementsByTagName("JBFY").item(0);
		if(jbfyElement == null){
			return "No Handling Court";
		}
		String courtName = jbfyElement.getAttribute("value");
		return courtName;
	}
	/*
	 * This function is called to get the level of the court.
	 */
	public String getCourtLevel(){
		Element wsElement = getWSElement();
		Element fyjbElement = (Element)wsElement.getElementsByTagName("FYJB").item(0);
		if(fyjbElement == null){
			return "No Court Level";
		}
		String courtLevel = fyjbElement.getAttribute("value");
		return courtLevel;
	}
	/*
	 * This function is called to get the area of the court.
	 */
	public String getCourtArea(){
		Element wsElement = getWSElement();
		Element xzqhpElement = (Element)wsElement.getElementsByTagName("XZQH_P").item(0);
		if(xzqhpElement == null){
			return "No Court Area";
		}
		String courtArea = xzqhpElement.getAttribute("value");
		return courtArea;
	}
	/*
	 * This function is called to get the type of this document.
	 */
	public String getDocumentName(){
		Element wsElement = getWSElement();
		Element wsmcElement = (Element)wsElement.getElementsByTagName("WSMC").item(0);
		String documentType = wsmcElement.getAttribute("value");
		return documentType;
	}
	/*
	 * This function is called to get the id of the case described in this document.
	 */
	public String getCaseID(){
		Element wsElement = getWSElement();
		Element ahElement = (Element)wsElement.getElementsByTagName("AH").item(0);
		String caseID = ahElement.getAttribute("value");
		return caseID;
	}
	/*
	 * This function is called to get the year of the case described in this document.
	 */
	public String getCaseYear(){
		Element wsElement = getWSElement();
		Element landElement = (Element)wsElement.getElementsByTagName("LAND").item(0);
		String caseYear = landElement.getAttribute("value");
		return caseYear;
	}
	/*
	 * This function is called to get the type of the case described in this document.
	 */
	public String getCaseType(){
		Element wsElement = getWSElement();
		Element ajxzElement = (Element)wsElement.getElementsByTagName("AJXZ").item(0);
		String caseType= ajxzElement.getAttribute("value");
		return caseType;
	}
	/*
	 * This function is called to get the type of this document.
	 */
	public String getDocumentType(){
		Element wsElement = getWSElement();
		Element wszlElement = (Element)wsElement.getElementsByTagName("WSZL").item(0);
		String documentType = wszlElement.getAttribute("value");
		return documentType;
	}
	/*
	 * This function is called to get the program of the case in this document.
	 */
	public String getCaseProgram(){
		Element wsElement = getWSElement();
		Element spcxElement = (Element)wsElement.getElementsByTagName("SPCX").item(0);
		String caseProgram = spcxElement.getAttribute("value");
		return caseProgram;
	}
	/*
	 * This function is called to get the detailed type of the case in this document.
	 */
	public String getCaseDetailedType(){
		Element wsElement = getWSElement();
		Element ajlxElement = (Element)wsElement.getElementsByTagName("AJLX").item(0);
		String caseDetailedType = ajlxElement.getAttribute("value");
		return caseDetailedType;
	}
	/*
	 * This function is called to get the node called "WS"
	 */
	private Element getWSElement(){
		NodeList nodeList = this.rootElement.getElementsByTagName("WS");
		Element element = (Element)nodeList.item(0);
		return element;
	}
	/*
	 * The result is splited by ";"
	 */
	public List<String> getParticipantList(){
		List<String> list = new ArrayList<String>();
		Element e0 = this.getSSCYRQJElement();
		if(e0 == null){
			return list;
		}
		NodeList nodelist = e0.getElementsByTagName("SSCYR");
		int size = nodelist.getLength();
		for(int i = 0;i < size;i++){
			Node node = nodelist.item(i);
			NodeList childlist = node.getChildNodes();
			int length = childlist.getLength();
			StringBuilder sb = new StringBuilder();
			for(int j = 0;j < length;j++){
				Node child = childlist.item(j);
				if(child.getNodeType() == Node.ELEMENT_NODE){
					Element childElement = (Element)child;
					String name = childElement.getAttribute("nameCN");
					String value = childElement.getAttribute("value");
					sb.append(name);
					sb.append(":");
					sb.append(value);
					sb.append(";");
				}
			}
			Element nodeElement = (Element)node;
			sb.append("描述全文:"+nodeElement.getAttribute("value"));
			list.add(sb.toString());
		}
		return list;
	}
	/*
	 * This function is called to get the node called "SSCYRQJ"
	 */
	private Element getSSCYRQJElement(){
		NodeList nodeList = this.rootElement.getElementsByTagName("SSCYRQJ");
		Element element = (Element)nodeList.item(0);
		return element;
	}
	/*
	 * This function is called to get the record of lawsuit.
	 */
	public String getLawsuitRecord(){
		NodeList nodelist = this.rootElement.getElementsByTagName("SSJL");
		Node node = nodelist.item(0);
		String result = DOMTool.traverse(node);
		result = result.substring(0, result.length()-1);
		return result;
	}
	/*
	 * This function is called to get the brief content of case
	 */
	public String getBriefCase(){
		String lawsuitRecord = this.getLawsuitRecord();
		String[] s = lawsuitRecord.split(";");
		for(int i = 0;i < s.length;i++){
			if(s[i].startsWith("案由:")){
				String result = s[i].substring(3);
				return result;
			}
		}
		for(int i = 0;i < s.length;i++){
			if(s[i].startsWith("指控罪名:")){
				String result = s[i].substring(5);
				return result;
			}
		}
		return "No Case Brief";
	}
	/*
	 * This function is called to get the basic condition of the case.
	 */
	public String getBasicConditionDescription(){
		NodeList nodelist = this.rootElement.getElementsByTagName("AJJBQK");
		Node node = nodelist.item(0);
		String result = DOMTool.traverse(node);
		result = result.substring(0, result.length()-1);
		return result;
	}
	/*
	 * This function is called to get the process of the analysis of the case.
	 */
	public String getAnalyzingProcess(){
		NodeList nodelist = this.rootElement.getElementsByTagName("CPFXGC");
		Node node = nodelist.item(0);
		if(node == null){
			return "No Analyzing Process";
		}
		String result = DOMTool.traverse(node);
		result = result.substring(0, result.length()-1);
		return result;
	}
	public List<String> getLawBasis(){
		List<String> result = new ArrayList<String>();
		String lawname = null;
		String lawnum = null;
		String ap = this.getAnalyzingProcess();
		String[] s = ap.split(";");
		for(int i = 0;i < s.length;i++){
			if(s[i].startsWith("法律法条名称:")){
				lawname = s[i].substring(7);
			}
			if(s[i].startsWith("条目:")){
				lawnum = s[i].substring(3);
				String line = lawname+" "+lawnum;
				result.add(line);
				lawnum = null;
			}
		}
		return result;
	}
	/*
	 * This function is called to get the result of the lawsuit.
	 */
	public String getLawsuitResult(){
		NodeList nodelist = this.rootElement.getElementsByTagName("CPJG");
		Node node = nodelist.item(0);
		String result = DOMTool.traverse(node);
		result = result.substring(0, result.length()-1);
		return result;
	}
	/*
	 * This function is called to get the end of this document.
	 */
	public String getDocumentEnd(){
		NodeList nodelist = this.rootElement.getElementsByTagName("WW");
		Node node = nodelist.item(0);
		String result = DOMTool.traverse(node);
		result = result.substring(0, result.length()-1);
		return result;
	}
	/*
	 * This function is called to get the judger of the case in this document.
	 */
	public List<String> getJudgerNames(){
		String end = this.getDocumentEnd();
		String[] s = end.split(";");
		List<String> list = new ArrayList<String>();
		for(int i = 0;i < s.length;i++){
			if(s[i].startsWith("审判人员姓名:")){
				String name = s[i].substring(7);
				list.add(name);
			}
		}
		return list;
	}
	public List<String> getLitigants(){
		List<String> list = new ArrayList<String>();
		List<String> pList = this.getParticipantList();
		for(String p:pList){
			String[] s = p.split(";");
			for(int i = 0;i < s.length;i++){
				if(s[i].startsWith("诉讼参与人名称:")){
					list.add(s[i].substring(8));
				}
			}
		}
		return list;
	}
	public Wenshu toWenshu(){
		Wenshu wenshu = new Wenshu();
		wenshu.setFilepath(getFilepath());
		wenshu.setCaseBrief(this.getBriefCase());
		wenshu.setCaseID(this.getCaseID());
		wenshu.setCaseProgram(this.getCaseProgram());
		wenshu.setCaseType(this.getCaseType());
		wenshu.setCaseYear(this.getCaseYear());
		wenshu.setCourtArea(this.getCourtArea());
		wenshu.setCourtLevel(this.getCourtLevel());
		wenshu.setCourtName(this.getHandlingCourt());
		wenshu.setDocumentName(this.getDocumentName());
		wenshu.setDocumentType(this.getDocumentType());
		wenshu.setFullText(this.getFullText());
		wenshu.setCaseName(this.getDocumentBeginning());
		wenshu.setLaws(this.getLawBasis());//TODO
		Map<String,String> map1 = new HashMap<String,String>();
		String end = this.getDocumentEnd();
		String[] s = end.split(";");
		String name = null;
		String job = null;
		for(int i = 0;i < s.length;i++){
			if(s[i].startsWith("审判人员姓名:")){
				name = s[i].substring(7);
			}
			if(s[i].startsWith("审判人员角色:")){
				job = s[i].substring(7);
				map1.put(name, job);
				name = null;
				job = null;
			}
		}
		wenshu.setJudgerInfo(map1);
		Map<String,String> map2 = new HashMap<String,String>();
		List<String> pList = this.getParticipantList();
		String name0 = null;
		String info0 = null;
		for(String p:pList){
			String[] e = p.split(";");
			for(int i = 0;i < e.length;i++){
				if(e[i].startsWith("诉讼参与人:")){
					info0 = e[i].substring(6);
				}
				if(e[i].startsWith("诉讼参与人名称:")){
					name0 = e[i].substring(8);
					map2.put(name0, info0);
					name0 = null;
					info0 = null;
				}
			}
		}
		wenshu.setParticipantInfo(map2);
//		String[] e = this.filepath.split("\\");
//		if(e.length == 0 || e == null)
//			wenshu.setFilename(null);
//		else
//			wenshu.setFilename(e[e.length-1]);
		return wenshu;
	}
}
