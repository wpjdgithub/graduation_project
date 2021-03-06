package Grad.Service.caseservice;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Grad.Bean.CaseBrief;
import Grad.Bean.CaseDetail;
import Grad.Bean.CaseJudgeCompare;
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
import Grad.Service.dataservice.jdbc.MySQLConnection;
import Grad.Service.dataservice.jdbc.MySQLConnectionImpl;
import Grad.Service.nlp.WenshuAnalyzingService;
import Grad.Service.nlp.impl.WenshuAnalyzingServiceImpl;
import Grad.Service.nlp.tool.Keywords;
import Grad.Service.wenshu.Wenshu;
import tfidf.DistanceCalc;
public class CaseServiceImpl implements CaseService{
	private CaseDataService caseDataService;
	private String path;
	public CaseServiceImpl(String path){
		this.path = path;
		this.caseDataService = new CaseDataServiceImpl(path);
	}
	@Override
	public CaseDetail uploadCase(String username, String filename, InputStream in) {
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
		String fileTitle;
		if(fileType.equals("docx")){
			reader = WenshuReaderTool.getWenshuReader(WenshuType.docx);
			fileTitle = filename.substring(0,filename.length()-5);
		}
		else if(fileType.equals("doc")){
			reader = WenshuReaderTool.getWenshuReader(WenshuType.doc);
			fileTitle = filename.substring(0,filename.length()-4);
		}
		else{
			reader = WenshuReaderTool.getWenshuReader(WenshuType.txt);
			fileTitle = filename.substring(0,filename.length()-4);
		}
		String content = reader.read(this.path+"tmp\\upload.tmp");
		CaseDetail res = new CaseDetail();
		ArrayList<CaseParagraph> paragraphs = new ArrayList<CaseParagraph>();
		String[] p = content.split("\n");
		for(String s: p){
			Sentence sentence = new Sentence();
			sentence.setValue(s);
			sentence.setNeedExplain(false);
			CaseParagraph paragraph = new CaseParagraph();
			paragraph.addSentence(sentence);
			paragraphs.add(paragraph);
		}
		res.setContext(paragraphs);
		CaseBrief caseBrief = new CaseBrief();
		String courtname = this.getCourtName(content);
		String brief = this.getCaseBrief(content);
		String wenshuType = this.getWenshuType(content);
		String caseid = this.getCaseID(content);
		String title = courtname+" "+wenshuType+" "+caseid;
		caseBrief.setType_text(wenshuType);
		caseBrief.setCourt(courtname);
		caseBrief.setBrief(brief);
		caseBrief.setTitle(title);
		res.setBrief(caseBrief);
		res.setRelatedLaw(this.getRelatedLaws(content));
		res.setRelatedCase(this.getRelatedCases(caseid, brief, content));
		MySQLConnection connection = new MySQLConnectionImpl("wenshu");
		connection.connect();
		Calendar c = Calendar.getInstance();
		String date = ""+c.get(Calendar.YEAR)+"/0"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH);
		connection.execute("insert into upload value('"+username+"','"+title+"','"+content+"','"+date+"','"+courtname
				+"','"+brief+"','"+wenshuType+"','"+caseid+"','"+res.getRelatedLaw().toString()+"','"+res.getRelatedCase().toString()+"');");
		connection.release();
		return res;
	}
	private ArrayList<CaseRelation> getRelatedCases(String caseid,String casebrief,String content){
		MySQLConnection connection = new MySQLConnectionImpl("wenshu");
		connection.connect();
		List<String> query = connection.query("select * from sim where caseid='"+caseid+"';");
		if(query.size() != 0){
			ArrayList<CaseRelation> res = new ArrayList<CaseRelation>();
			String[] s = query.get(0).split(" ");
			for(int i = 1;i < 6;i++){
				CaseRelation relation = new CaseRelation();
				String v = s[i];
				relation.setId(v);
				relation.setTitle(v);
				res.add(relation);
			}
			return res;
		}
		Wenshu wenshu = new Wenshu();
		wenshu.setCaseID(caseid);
		wenshu.setCaseBrief(casebrief);
		wenshu.setFullText(content);
		WenshuAnalyzingService service = new WenshuAnalyzingServiceImpl();
		DistanceCalc calc = new DistanceCalc();
		Map<String,Double> map = service.calculateTFIDF(wenshu);
		String sql1 = "select caseid,tfidf from tfidf where casebrief='"+casebrief+"';";
		List<String> list1 = connection.query(sql1);
		Map<String,Double> distMap = new HashMap<String,Double>();
		for(String s: list1){
			String[] e = s.split(" ");
			String id = e[0];
			Map<String,Double> vec = new HashMap<String,Double>();
			String[] v = e[1].split(";");
			for(int i =0;i < v.length;i++){
				String[] v0 = v[i].split("=");
				vec.put(v0[0], Double.parseDouble(v0[1]));
			}
			double dist = calc.calculateCosDistance(map, vec);
			distMap.put(id, dist);
		}
		List<Map.Entry<String, Double>> list = 
				new ArrayList<Map.Entry<String,Double>>(distMap.entrySet());
		Collections.sort(list,new Comparator<Map.Entry<String, Double>>(){
			@Override
			public int compare(Entry<String, Double> arg0, Entry<String, Double> arg1) {
				double v0 = arg0.getValue();
				double v1 = arg1.getValue();
				if(v0 < v1)
					return 1;
				else if(v0 > v1)
					return -1;
				else
					return 0;
			}
		});
		ArrayList<CaseRelation> res = new ArrayList<CaseRelation>();
		for(int i = 0;i < 5;i++){
			CaseRelation relation = new CaseRelation();
			String v = list.get(i).getKey();
			relation.setId(v);
			relation.setTitle(v);
			res.add(relation);
		}
		String insertSQL = "insert into sim value('"+caseid+"','"+res.get(0).getId()
				+"','"+res.get(1).getId()+"','"+res.get(2).getId()+"','"+res.get(3).getId()+"','"+res.get(4).getId()+"');";
		connection.execute(insertSQL);
		connection.release();
		return res;
	}
	private ArrayList<CaseRelation> getRelatedLaws(String content){
		ArrayList<CaseRelation> res = new ArrayList<CaseRelation>();
		String sql = "select name from law;";
		MySQLConnection connection = new MySQLConnectionImpl("wenshu");
		connection.connect();
		List<String> queryResult = connection.query(sql);
		connection.release();
		Map<Integer,String> temp = new HashMap<Integer,String>();
		for(String lawname: queryResult){
			int index = content.indexOf(lawname);
			if(index != -1){
				temp.put(index, lawname);
			}
		}
		Pattern pattern = Pattern.compile("(第[^条]*条)");
		Matcher matcher = pattern.matcher(content);
		List<String> matches = new ArrayList<String>();
		while(matcher.find()){
			matches.add(matcher.group());
		}
		for(String match: matches){
			int index = content.indexOf(match);
			if(index != -1){
				temp.put(index, match);
			}
		}
		List<Map.Entry<Integer, String>> list = 
				new ArrayList<Map.Entry<Integer, String>>(temp.entrySet());
		Collections.sort(list,new Comparator<Map.Entry<Integer,String>>(){
			@Override
			public int compare(Entry<Integer, String> arg0, Entry<Integer, String> arg1) {
				int i0 = arg0.getKey();
				int i1 = arg1.getKey();
				if(i0 > i1)
					return 1;
				else if(i0 < i1)
					return -1;
				else
					return 0;
			}
		});
		String lawname = null;
		for(Map.Entry<Integer, String> entry: list){
			String s = entry.getValue();
			if(s.startsWith("第")){
				if(s.length() > 20)
					continue;
				String line = lawname+" "+s.substring(1, s.length()-1);
				CaseRelation relation = new CaseRelation();
				relation.setId(line);
				relation.setTitle(line);
				res.add(relation);
			}
			else{
				lawname = s;
			}
		}
		return res;
	}
	private String getCourtName(String content){
		String result = null;
		MySQLConnection connection = new MySQLConnectionImpl("wenshu");
		connection.connect();
		String sql = "select name from court;";
		List<String> courts = connection.query(sql);
		for(String court: courts){
			if(content.contains(court)){
				result = court;
				break;
			}
		}
		connection.release();
		return result;
	}
	private String getCaseBrief(String content){
		String result = null;
		MySQLConnection connection = new MySQLConnectionImpl("wenshu");
		connection.connect();
		String sql = "select name,type from brief;";
		List<String> list = new ArrayList<String>();
		List<String> briefs = connection.query(sql);
		for(String line: briefs){
			String[] s = line.split(" ");
			if(content.contains(s[0])){
				list.add(s[0]);
			}
		}
		if(result == null){
			result = list.get(list.size()-1);
		}
		connection.release();
		return result;
	}
	private String getWenshuType(String content){
		if(content.contains("民 事 判 决 书")){
			return "民事判决书";
		}
		else if(content.contains("民 事 裁 定 书")){
			return "民事裁定书";
		}
		else if(content.contains("民 事 调 解 书")){
			return "民事调解书";
		}
		else if(content.contains("刑 事 判 决 书")){
			return "刑事判决书";
		}
		else if(content.contains("决 定 书")){
			return "决定书";
		}
		else{
			return "default";
		}
	}
	private String getCaseID(String content){
		int start = content.indexOf("（");
		int end = content.indexOf("号");
		if(start > end){
			start = content.indexOf("(");
		}
		String result = content.substring(start, end+1);
		return result;
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
		if(id.contains(" ")){
			return this.getCaseUploadedByTitle(id);
		}
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
		ArrayList<CaseParagraph> paragraphs = this.fullText2List(wenshu.getFullText());
		caseDetail.setContext(paragraphs);
		ArrayList<CaseRelation> relatedCases = this.getSimilarCases(wenshu);
		caseDetail.setRelatedCase(relatedCases);
		ArrayList<CaseRelation> relatedLaws = this.getRelatedLaws(wenshu);
		caseDetail.setRelatedLaw(relatedLaws);
		return caseDetail;
	}
	private CaseDetail getCaseUploadedByTitle(String id){
		CaseDetail res = new CaseDetail();
		CaseBrief brief = new CaseBrief();
		brief.setTitle(id);
		MySQLConnection connection = new MySQLConnectionImpl("wenshu");
		connection.connect();
		String sql = "select courtname,brief,type,caseid from upload where casetitle='"+id+"';";
		String queryResult = connection.query(sql).get(0);
		String[] e = queryResult.split(" ");
		brief.setCourt(e[0]);
		brief.setBrief(e[1]);
		brief.setType_text(e[2]);
		brief.setId(e[3]);
		//获取正文内容
		String content = connection.query("select casecontext from upload where casetitle='"+id+"';").get(0);
		ArrayList<CaseParagraph> paragraphs = this.fullText2List(content);
		//获取相关法律
		String relatedLawList = connection.query("select relatedlaws from upload where casetitle='"+id+"';").get(0);
		relatedLawList = relatedLawList.substring(1,relatedLawList.length()-1);//去除首尾的[]
		String[] relatedLaw = relatedLawList.split(",");
		ArrayList<CaseRelation> relatedLaws = new ArrayList<CaseRelation>();
		for(int i = 0;i < relatedLaw.length;i++){
			CaseRelation relation = new CaseRelation();
			relation.setId(relatedLaw[i].trim());
			relation.setTitle(relatedLaw[i].trim());
			relatedLaws.add(relation);
		}
		//获取相似案件
		String relatedCaseList = connection.query("select relatedcases from upload where casetitle='"+id+"';").get(0);
		relatedCaseList = relatedCaseList.substring(1,relatedCaseList.length()-1);
		String[] relatedCase = relatedCaseList.split(",");
		ArrayList<CaseRelation> relatedCases = new ArrayList<CaseRelation>();
		for(int i = 0;i < relatedCase.length;i++){
			CaseRelation relation = new CaseRelation();
			relation.setId(relatedCase[i].trim());
			relation.setTitle(relatedCase[i].trim());
			relatedCases.add(relation);
		}
		connection.release();
		res.setBrief(brief);
		res.setContext(paragraphs);
		res.setRelatedLaw(relatedLaws);
		res.setRelatedCase(relatedCases);
		return res;
	}
	private ArrayList<CaseParagraph> fullText2List(String fullText){
		ArrayList<CaseParagraph> list = new ArrayList<CaseParagraph>();
		String[] lines = fullText.split("\n");
		if(lines == null){
			CaseParagraph paragraph = new CaseParagraph();
			Sentence sentence = new Sentence(fullText,false,null);
			paragraph.addSentence(sentence);
			list.add(paragraph);
			return list;
		}
		for(int i = 0;i < lines.length;i++){
			if(lines[i].equals(""))
				continue;
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
	@Override
	public List<CaseJudgeCompare> getJudgeMes(String id) {
		List<CaseJudgeCompare> res = new ArrayList<CaseJudgeCompare>();
		MySQLConnection connection = new MySQLConnectionImpl("wenshu");
		connection.connect();
		String fullText = null;
		String caseBrief = null;
		if(id.contains(" ")){
			id = id.split(" ")[2];
			fullText = connection.query("select casecontext from upload where caseid='"+id+"';").get(0);
			caseBrief = connection.query("select brief from upload where caseid='"+id+"';").get(0);
		}
		else{
			fullText = connection.query("select content from wenshu where caseid='"+id+"';").get(0);
			caseBrief = connection.query("select brief from wenshu where caseid='"+id+"';").get(0);
		}
		Wenshu wenshu = new Wenshu();
		wenshu.setCaseBrief(caseBrief);
		wenshu.setCaseID(id);
		wenshu.setFullText(fullText);
		Map<String,Double> vec = new WenshuAnalyzingServiceImpl().calculateTFIDF(wenshu);
		List<String> idList = this.caseDataService.getCaseIDsByBrief(caseBrief);
		DistanceCalc dc = new DistanceCalc();
		Map<String,Double> simMap = new HashMap<String,Double>();
		WenshuDataService wenshuDataService = new WenshuDataServiceImpl();
		for(String caseid:idList){
			Map<String,Double> vector = wenshuDataService.getTFIDFVector(caseid);
			double distance = dc.calculateCosDistance(vec, vector);
			simMap.put(caseid, distance);
			if(simMap.size() > 400)
				break;
			System.out.println(simMap.size());
		}
		List<Map.Entry<String, Double>> list = 
				new ArrayList<Map.Entry<String,Double>>(simMap.entrySet());
		Collections.sort(list,new Comparator<Map.Entry<String, Double>>(){
			@Override
			public int compare(Entry<String, Double> arg0, Entry<String, Double> arg1) {
				double d0 = arg0.getValue();
				double d1 = arg1.getValue();
				if(d0 < d1){
					return 1;
				}
				else if(d0 > d1){
					return -1;
				}
				else{
					return 0;
				}
			}
		});
		for(Map.Entry<String, Double> entry: list){
			int alike = 0;
			double value = entry.getValue();
			if(value < 0.15){
				continue;
			}
			else if(value > 0.4){
				alike = 1;
			}
			else if(value <= 0.4 && value > 0.18){
				alike = 2;
			}
			else{
				alike = 3;
			}
			String caseid = entry.getKey();
			String title = null;
			try{
			    title = connection.query("select casename from wenshu where caseid='"+caseid+"';").get(0);
			} catch(IndexOutOfBoundsException e) {
				continue;
			}
			String date = connection.query("select date from wenshu where caseid='"+caseid+"';").get(0);
			String core = connection.query("select vector from newtfidf where caseid='"+caseid+"';").get(0);
			StringBuilder sb = new StringBuilder();
			String[] lines = core.split(";");
			int count = 0;
			for(String line: lines){
				String[] s = line.split("=");
				double v = Double.parseDouble(s[1]);
				if(v != 0.0 && fullText.contains(s[0])){
					sb.append(s[0]).append(" ");
					count++;
					if(count > 15)
						break;
				}
			}
			String same_core = sb.toString().trim();
			CaseJudgeCompare compare = new CaseJudgeCompare();
			compare.setAlike(alike);
			compare.setDate(date.split("/")[0]);
			compare.setId(caseid);
			compare.setSame_core(same_core);
			compare.setTitle(title);
			res.add(compare);
			System.out.println(entry.getKey()+" "+entry.getValue());
		}
		connection.release();
		return res;
	}
}