package Grad.Service.caseservice;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import Grad.Bean.CaseBrief;
import Grad.Bean.CaseDetail;
import Grad.Bean.CaseMinMes;
import Grad.Bean.CaseParagraph;
import Grad.Bean.CaseRelation;
import Grad.Bean.Sentence;
import Grad.Service.CaseService;
import Grad.Service.dataservice.WenshuDataService;
import Grad.Service.dataservice.impl.WenshuDataServiceImpl;
import Grad.Service.nlp.lawdict.LawService;
import Grad.Service.nlp.lawdict.LawServiceImpl;
import Grad.Service.nlp.tool.Keywords;
import Grad.Service.wenshu.Wenshu;

public class CaseServiceImpl implements CaseService{
	
	private String path;
	
	public CaseServiceImpl(String path){
		this.path = path;
	}

	@Override
	public boolean uploadCase(String username, InputStream in) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<CaseMinMes> getCaseByUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CaseDetail getCaseByTitle(String id) {
		WenshuDataService wenshuDataService = new WenshuDataServiceImpl(this.path);
		Wenshu wenshu = wenshuDataService.getWenshuByCaseID(id);
		CaseDetail caseDetail = new CaseDetail();
		CaseBrief caseBrief = new CaseBrief();
		caseBrief.setBrief(wenshu.getCaseBrief());
		Keywords keywords = new Keywords(this.path);
		Iterator<String> iterator = keywords.getKeywordsIterator();
		while(iterator.hasNext()){
			String keyword = iterator.next();
			if(wenshu.getFullText().contains(keyword)){
				wenshu.addKeyword(keyword);
			}
		}
		caseBrief.setCore(wenshu.getKeywords());
		caseBrief.setCourt(wenshu.getCourtName());
//		caseBrief.setDate(wenshu.getDate());//TODO
		caseBrief.setProcess_judgement(wenshu.getCaseProgram());
		caseBrief.setSource("天津最高人民法院");
		caseBrief.setTitle(wenshu.getCaseName());
		caseBrief.setType_text(wenshu.getDocumentType());
		caseDetail.setBrief(caseBrief);
		//案例内容
		ArrayList<CaseParagraph> paragraphs = this.fullText2List(wenshu);
		caseDetail.setContext(paragraphs);
		//相关案例
		ArrayList<CaseRelation> relatedCases = this.getSimilarCases();
		caseDetail.setRelatedCase(relatedCases);
		//相关法律
		ArrayList<CaseRelation> relatedLaws = this.getRelatedLaws(wenshu);
		caseDetail.setRelatedLaw(relatedLaws);
		return caseDetail;
	}
	
	private ArrayList<CaseParagraph> fullText2List(Wenshu wenshu){
		ArrayList<CaseParagraph> list = new ArrayList<CaseParagraph>();
		LawService lawService = new LawServiceImpl(this.path);
		String fullText = wenshu.getFullText();
		String[] lines = fullText.split("\n");
		for(int i = 0;i < lines.length;i++){
			char startChar = lines[i].charAt(0);
			int temp = (int)startChar;
			if(temp != 13 && temp != 32){
				String line = lines[i].trim();
				CaseParagraph paragraph = new CaseParagraph();
				List<Integer> splitIndexs = new ArrayList<Integer>();//用于存放分割的index
				List<String> relatedLaws = wenshu.getLaws();
				for(int j = 0;j < relatedLaws.size();j++){
					String relatedLaw = relatedLaws.get(j);
					String[] s = relatedLaw.split(" ");
					String lawname = s[0];
					String lawnumber = s[1];
					if(line.contains(lawname)){
						int startIndex = fullText.indexOf(lawname);
						int endIndex = startIndex + lawname.length();
						splitIndexs.add(startIndex);
						splitIndexs.add(endIndex);
					}
					if(line.contains(lawnumber)){
						int startIndex = fullText.indexOf(lawnumber);
						int endIndex = startIndex + lawnumber.length();
						splitIndexs.add(startIndex);
						splitIndexs.add(endIndex);
					}
				}
				if(!splitIndexs.contains(0))
					splitIndexs.add(0);
				if(!splitIndexs.contains(line.length()))
					splitIndexs.add(line.length());
				Collections.sort(splitIndexs);
				//下面开始划分段落
//				if(splitIndexs.size() == 0){
//					Sentence sentence = new Sentence(line,false,null);
//					paragraph.addSentence(sentence);
//				}
//				else{
//					String lawname = null;
//					for(int j = 0;j < splitIndexs.size()-1;j++){
//						int startIndex = splitIndexs.get(j);
//						int endIndex = splitIndexs.get(j+1);
//						String substr = line.substring(startIndex, endIndex);
//						if(substr.startsWith("中华人民共和国")){
//							//TODO:加上法律的说明
//							String explanation = "法典说明";
//							lawname = substr;
//							Sentence sentence = new Sentence(substr,true,explanation);
//							paragraph.addSentence(sentence);
//						}
//						else if(isChineseInteger(substr)){
//							String explanation = lawService.getLawContent(lawname, substr);
//							Sentence sentence = new Sentence(substr,true,explanation);
//							paragraph.addSentence(sentence);
//						}
//						else{
//							Sentence sentence = new Sentence(substr,false,null);
//							paragraph.addSentence(sentence);
//						}
//					}
//				}
				list.add(paragraph);
			}
		}
		return list;
	}
	private boolean isChineseInteger(String s){
		Set<Character> set = new HashSet<Character>();
		set.add('一');
		set.add('二');
		set.add('三');
		set.add('四');
		set.add('五');
		set.add('六');
		set.add('七');
		set.add('八');
		set.add('九');
		set.add('十');
		set.add('零');
		int length = s.length();
		for(int i = 0;i < length;i++){
			char c = s.charAt(i);
			if(!set.contains(c)){
				return false;
			}
		}
		return true;
	}
	//TODO
	private ArrayList<CaseRelation> getSimilarCases(){
		return new ArrayList<CaseRelation>();
	}
	
	private ArrayList<CaseRelation> getRelatedLaws(Wenshu wenshu){
		List<String> laws = wenshu.getLaws();
		ArrayList<CaseRelation> relatedLaws = new ArrayList<CaseRelation>();
		int size = laws.size();
		for(int i = 0;i < size;i++){
			String law = laws.get(i);
			CaseRelation caseRelation = new CaseRelation();
			caseRelation.setId(i);
			caseRelation.setTitle(law);
			relatedLaws.add(caseRelation);
		}
		return relatedLaws;
	}
	
	//Test
	public static void main(String[] args){
		CaseService service = new CaseServiceImpl("F:\\Programming.Project\\GitRepo\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\graduation_project\\");
		CaseDetail caseDetail = service.getCaseByTitle("(2013)南刑初字第21号");
		System.out.println(caseDetail.getBrief().getCore());
	}

}
