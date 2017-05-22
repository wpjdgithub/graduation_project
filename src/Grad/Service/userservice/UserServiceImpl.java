package Grad.Service.userservice;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Grad.Bean.CaseCompare;
import Grad.Bean.CaseMinMes;
import Grad.Bean.CaseUploadDetail;
import Grad.Bean.UserInfo;
import Grad.Service.UserSerivice;
import Grad.Service.dataservice.CaseDataService;
import Grad.Service.dataservice.UserDataService;
import Grad.Service.dataservice.impl.CaseDataServiceImpl;
import Grad.Service.dataservice.impl.UserDataServiceImpl;
import Grad.Service.dataservice.impl.WenshuDataServiceImpl;
import Grad.Service.dataservice.jdbc.MySQLConnection;
import Grad.Service.dataservice.jdbc.MySQLConnectionImpl;
import Grad.Service.nlp.WenshuAnalyzingService;
import Grad.Service.nlp.impl.WenshuAnalyzingServiceImpl;
import Grad.Service.wenshu.Wenshu;
import tfidf.DistanceCalc;
public class UserServiceImpl implements UserSerivice{
	private UserDataService userDataService;
	private CaseDataService caseDataService;
	private WenshuAnalyzingService wenshuAnalyzingService;
	private MySQLConnection connection;
	public UserServiceImpl(){
		this.userDataService = new UserDataServiceImpl();
		this.wenshuAnalyzingService = new WenshuAnalyzingServiceImpl();
		this.caseDataService = new CaseDataServiceImpl();
		this.connection = new MySQLConnectionImpl("wenshu");
		this.connection.connect();
	}
	@Override
	public boolean register(UserInfo info) {
		UserInfo u = this.userDataService.getUserInfo(info.getUsername());
		if(u != null){
			return false;
		}
		else{
			this.userDataService.insert(info);
			return true;
		}
	}
	@Override
	public boolean login(UserInfo info) {
		UserInfo userInfo = this.userDataService.getUserInfo(info.getUsername());
		if(userInfo == null){
			return false;
		}
		else{
			if(userInfo.getPassword().equals(info.getPassword())){
				return true;
			}
			else{
				return false;
			}
		}
	}
	@Override
	public UserInfo getInfo(String username) {
		UserInfo userInfo = this.userDataService.getUserInfo(username);
		if(userInfo == null){
			return new UserInfo("default","default","default");
		}
		else{
			return userInfo;
		}
	}
	@Override
	public List<CaseMinMes> getMinMes(String username) {
		List<CaseMinMes> result = new ArrayList<CaseMinMes>();
		List<String> list = this.userDataService.getCaseMinMes(username);
		int size = list.size();
		for(int i = 0;i < size;i++){
			String line = list.get(i);
			String[] s = line.split(" ");
			CaseMinMes mes = new CaseMinMes();
			StringBuilder sb = new StringBuilder();
			for(int j = 0;j < s.length-1;j++){
				sb.append(s[j]).append(" ");
			}
			String title = sb.toString().trim();
			mes.setId(title);
			mes.setTitle(title);
			mes.setUploadDate(s[s.length-1]);
			result.add(mes);
		}
		return result;
	}
	@Override
	public List<CaseCompare> getCompareMes(String username, String id, int type) {
		if(type == 1){
			return this.getCompareMes1(username, id);
		}
		else if(type == 2){
			return this.getCompareMes2(username, id);
		}
		else{
			return new ArrayList<CaseCompare>();
		}
	}
	// type为1时  依据用户名和该用户上传的一个文书，查询这个文书与其他文书之间的相似度 
	private List<CaseCompare> getCompareMes1(String username,String id){
		id = id.split(" ")[2];
		List<CaseCompare> res = new ArrayList<CaseCompare>();
		List<CaseUploadDetail> uploads = this.caseDataService.getCaseUploadDetail(username);
		CaseUploadDetail detail0 = this.caseDataService.getUploadedCase(id);
		DistanceCalc dc = new DistanceCalc();
		String fullText = detail0.getCaseContext();
		String caseBrief = connection.query("select brief from upload where caseid='"+id+"';").get(0);
		Wenshu wenshu = new Wenshu();
		wenshu.setCaseBrief(caseBrief);
		wenshu.setCaseID(id);
		wenshu.setFullText(fullText);
		Map<String,Double> vec0 = this.wenshuAnalyzingService.calculateTFIDF(wenshu);
		for(CaseUploadDetail upload: uploads){
			String caseid = upload.getCaseTitle().split(" ")[2];
			if(caseid.equals(id))
				continue;
			String title = upload.getCaseTitle();
			fullText = connection.query("select casecontext from upload where caseid='"+caseid+"';").get(0);
			caseBrief = connection.query("select brief from upload where caseid='"+caseid+"';").get(0);
			wenshu.setCaseBrief(caseBrief);
			wenshu.setCaseID(id);
			wenshu.setFullText(fullText);
			Map<String,Double> vec = this.wenshuAnalyzingService.calculateTFIDF(wenshu);
			double rate = dc.calculateCosDistance(vec0, vec);
			CaseCompare compare = new CaseCompare(caseid,title,rate);
			res.add(compare);
		}
		return res;
	}
	// type为2时  依据这2个信息查询系统文书与该文书的相似案例，并将其他文书与搜索到的相关案例进行检测
	private List<CaseCompare> getCompareMes2(String username,String id){
		id = id.split(" ")[2];
		List<CaseCompare> res = new ArrayList<CaseCompare>();
		List<CaseUploadDetail> uploads = this.caseDataService.getCaseUploadDetail(username);//其他文书
		List<String> simIDs = this.caseDataService.getSimilarCases(id);
		String simId = simIDs.get(0);
		Map<String,Double> vec0 = new WenshuDataServiceImpl().getTFIDFVector(simId);
		DistanceCalc dc = new DistanceCalc();
		for(CaseUploadDetail upload: uploads){
			String caseid = upload.getCaseTitle().split(" ")[2];
			if(caseid.equals(id))
				continue;
			String title = upload.getCaseTitle();
			String fullText = connection.query("select casecontext from upload where caseid='"+caseid+"';").get(0);
			String caseBrief = connection.query("select brief from upload where caseid='"+caseid+"';").get(0);
			Wenshu wenshu = new Wenshu();
			wenshu.setCaseBrief(caseBrief);
			wenshu.setCaseID(id);
			wenshu.setFullText(fullText);
			Map<String,Double> vec = this.wenshuAnalyzingService.calculateTFIDF(wenshu);
			double rate = dc.calculateCosDistance(vec0, vec);
			CaseCompare compare = new CaseCompare(caseid,title,rate);
			res.add(compare);
		}
		return res;
	}
}