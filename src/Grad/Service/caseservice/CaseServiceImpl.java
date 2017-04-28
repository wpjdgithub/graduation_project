package Grad.Service.caseservice;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import Grad.Bean.CaseBrief;
import Grad.Bean.CaseDetail;
import Grad.Bean.CaseMinMes;
import Grad.Bean.CaseParagraph;
import Grad.Bean.CaseRelation;
import Grad.Bean.CaseUploadDetail;
import Grad.Bean.Sentence;
import Grad.Service.CaseService;
import Grad.Service.caseservice.reader.FileTypeJudge;
import Grad.Service.caseservice.reader.IWenshuReader;
import Grad.Service.caseservice.reader.WenshuReaderTool;
import Grad.Service.caseservice.reader.WenshuType;
import Grad.Service.dataservice.CaseDataService;
import Grad.Service.dataservice.WenshuDataService;
import Grad.Service.dataservice.impl.CaseDataServiceImpl;
import Grad.Service.dataservice.impl.WenshuDataServiceImpl;
import Grad.Service.nlp.tool.Keywords;
import Grad.Service.wenshu.Wenshu;
public class CaseServiceImpl implements CaseService{
	private CaseDataService caseDataService;
	private String path;
	public CaseServiceImpl(String path){
		this.path = path;
		this.caseDataService = new CaseDataServiceImpl(path);
	}
	@Override
	public CaseDetail uploadCase(String username, InputStream in) {
		String fileType = null;
		try {
			FileOutputStream os = new FileOutputStream(this.path+"tmp\\upload.tmp");
			FileInputStream is = (FileInputStream)in;
			FileChannel inChannel = is.getChannel();
			FileChannel outChannel = os.getChannel();
			inChannel.transferTo(0, inChannel.size(), outChannel);
			inChannel.close();
			is.close();
			outChannel.close();
			os.close();
			InputStream in2 = new FileInputStream(this.path+"tmp\\upload.tmp");
			byte[] b = new byte[10];
			in2.read(b, 0, b.length);
			String hex = FileTypeJudge.bytesToHexString(b);
			fileType = FileTypeJudge.getFileType(hex);
			in2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		IWenshuReader reader;
		if(fileType.equals("docx")){
			reader = WenshuReaderTool.getWenshuReader(WenshuType.docx);
		}
		else if(fileType.equals("doc")){
			reader = WenshuReaderTool.getWenshuReader(WenshuType.doc);
		}
		else{
			reader = WenshuReaderTool.getWenshuReader(WenshuType.txt);
		}
		List<String> content = reader.read(this.path+"tmp\\upload.tmp");
		StringBuilder sb = new StringBuilder
		return null;
	}
	@Override
	public ArrayList<CaseMinMes> getCaseByUser(String username) {
		List<CaseUploadDetail> list = this.caseDataService.getCaseUploadDetail(username);
		ArrayList<CaseMinMes> result = new ArrayList<CaseMinMes>();
		int size = list.size();
		for(int i = 0;i < size;i++){
			CaseUploadDetail detail = list.get(i);
			CaseMinMes mes = detail.toCaseMinMes();
			result.add(mes);
		}
		return result;
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
		caseBrief.setCourt(wenshu.getCaseName().split(" ")[0]);
		caseBrief.setDate(wenshu.getCaseDate());
		caseBrief.setProcess_judgement(wenshu.getCaseProgram());
		caseBrief.setSource("天津最高人民法院");
		caseBrief.setTitle(wenshu.getCaseName());
		caseBrief.setType_text(wenshu.getDocumentType());
		caseDetail.setBrief(caseBrief);
		ArrayList<CaseParagraph> paragraphs = this.fullText2List(wenshu);
		caseDetail.setContext(paragraphs);
		ArrayList<CaseRelation> relatedCases = this.getSimilarCases(wenshu);
		caseDetail.setRelatedCase(relatedCases);
		ArrayList<CaseRelation> relatedLaws = this.getRelatedLaws(wenshu);
		caseDetail.setRelatedLaw(relatedLaws);
		return caseDetail;
	}
	private ArrayList<CaseParagraph> fullText2List(Wenshu wenshu){
		ArrayList<CaseParagraph> list = new ArrayList<CaseParagraph>();
		String fullText = wenshu.getFullText();
		String[] lines = fullText.split("\n");
		if(lines == null){
			CaseParagraph paragraph = new CaseParagraph();
			Sentence sentence = new Sentence(fullText,false,null);
			paragraph.addSentence(sentence);
			list.add(paragraph);
			return list;
		}
		for(int i = 0;i < lines.length;i++){
			char startChar = lines[i].charAt(0);
			int temp = (int)startChar;
			if(temp != 13 && temp != 32){
				String line = lines[i].trim();
				CaseParagraph paragraph = new CaseParagraph();
				Sentence sentence = new Sentence(line,false,null);
				paragraph.addSentence(sentence);
				list.add(paragraph);
			}
		}
		return list;
	}
	private ArrayList<CaseRelation> getSimilarCases(Wenshu wenshu){
		ArrayList<CaseRelation> result = new ArrayList<CaseRelation>();
		List<String> list = this.caseDataService.getSimilarCases(wenshu.getCaseID());
		for(String s: list){
			try {
				s = new String(s.getBytes(),"GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			System.out.println(s);
			CaseRelation relation = new CaseRelation();
			relation.setId(s);
			relation.setTitle(s);
			result.add(relation);
		}
		return result;
	}
	private ArrayList<CaseRelation> getRelatedLaws(Wenshu wenshu){
		List<String> laws = wenshu.getLaws();
		ArrayList<CaseRelation> relatedLaws = new ArrayList<CaseRelation>();
		int size = laws.size();
		for(int i = 0;i < size;i++){
			String law = laws.get(i);
			CaseRelation caseRelation = new CaseRelation();
			caseRelation.setId(law);
			caseRelation.setTitle(law);
			relatedLaws.add(caseRelation);
		}
		return relatedLaws;
	}
	@Override
	public boolean deleteCase(String[] list) {
		this.caseDataService.deleteCases(list);
		return true;
	}
}