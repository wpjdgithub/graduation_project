package Grad.Service.caseservice;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import Grad.Bean.CaseBrief;
import Grad.Bean.CaseDetail;
import Grad.Bean.CaseMinMes;
import Grad.Bean.CaseParagraph;
import Grad.Bean.CaseRelation;
import Grad.Service.CaseService;
import Grad.Service.dataservice.WenshuDataService;
import Grad.Service.dataservice.impl.WenshuDataServiceImpl;
import Grad.Service.nlp.NLPService;
import Grad.Service.nlp.impl.NLPServiceImpl;
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
		//相关法律
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
		caseDetail.setRelatedLaw(relatedLaws);
		return caseDetail;
	}
	
	private ArrayList<CaseParagraph> fullText2List(Wenshu wenshu){
		ArrayList<CaseParagraph> list = new ArrayList<CaseParagraph>();
		String fullText = wenshu.getFullText();
		String[] line = fullText.split("\n");
		for(int i = 0;i < line.length;i++){
			char startChar = line[i].charAt(0);
			int temp = (int)startChar;
			if(temp != 13 && temp != 32)
				System.out.println(line[i].trim());
		}
		return list;
	}
	
	//Test
	public static void main(String[] args){
		CaseService service = new CaseServiceImpl("F:\\Programming.Project\\GitRepo\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\graduation_project\\");
		CaseDetail caseDetail = service.getCaseByTitle("(2013)南刑初字第21号刑事判决书（一审公诉案件适用普通程序用）.doc.xml");
		System.out.println(caseDetail.getBrief().getBrief());
	}

}
