package Grad.Service.dataservice.impl;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.lucene.queryparser.classic.ParseException;
import Grad.Service.dataservice.WenshuDataService;
import Grad.Service.dataservice.jdbc.MySQLConnection;
import Grad.Service.dataservice.jdbc.MySQLConnectionImpl;
import Grad.Service.searchservice.lucene.SearchFiles;
import Grad.Service.searchservice.lucene.SearchItem;
import Grad.Service.wenshu.Wenshu;
import Grad.Service.xml.WenshuXMLObject;
public class WenshuDataServiceImpl implements WenshuDataService{
	private String path = "F:/Programming.Project/data/";
	public WenshuDataServiceImpl(){
	}
	public WenshuDataServiceImpl(String path){
		this.path = path;
	}
	public void setPath(String path){
		this.path = path;
	}
	@Override
	public List<Wenshu> getAllWenshuData() {
		List<Wenshu> list = new LinkedList<Wenshu>();
		File rootDataDir = new File(this.path+"wenshudata\\");
		File[] mainDir = rootDataDir.listFiles();
		for(int i = 0;i < mainDir.length;i++){
			if(!mainDir[i].isDirectory())
				continue;
			File[] detailDir = mainDir[i].listFiles();
			for(int j = 0;j < detailDir.length;j++){
				if(!detailDir[j].isDirectory())
					continue;
				String[] filename = detailDir[j].list();
				for(int k = 0;k < filename.length;k++){
					String filepath = this.path+"wenshudata\\"+mainDir[i].getName()+"\\"
							+detailDir[j].getName()+"\\"+filename[k];
					System.out.println(filepath);
					WenshuXMLObject wenshuXML = new WenshuXMLObject(filepath);
					Wenshu wenshu = wenshuXML.toWenshu();
					list.add(wenshu);
				}
			}
		}
		return list;
	}
	@Override
	public List<Wenshu> getSmallTestSet(String dirname) {
		List<Wenshu> list = new LinkedList<Wenshu>();
		File dir0 = new File(this.path+"wenshudata\\"+dirname+"\\");
		File[] dir1 = dir0.listFiles();
		for(File dir:dir1){
			if(!dir.isDirectory()){
				continue;
			}
			String[] filenames = dir.list();
			for(String filename:filenames){
				String filepath =this.path + "wenshudata\\"+dir0.getName()+"\\"+dir.getName()+"\\"+filename;
				System.out.println(filepath);
				WenshuXMLObject wenshuXML = new WenshuXMLObject(filepath);
				Wenshu wenshu = wenshuXML.toWenshu();
				list.add(wenshu);
			}
		}
		return list;
	}
	@Override
	public Wenshu getWenshuByCaseID(String caseID) {
		SearchFiles searchTool = new SearchFiles(this.path);
		Wenshu wenshu = null;
		try {
			List<SearchItem> list1 = new ArrayList<SearchItem>();
			List<String> list2 = new ArrayList<String>();
			list1.add(SearchItem.caseid);
			list1.add(SearchItem.fulltext);
			list2.add(caseID);
			list2.add(caseID);
			wenshu = searchTool.search(list1,list2).get(0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return wenshu;
	}
	public static void main(String[] args){
		WenshuDataService wenshuDataService = new WenshuDataServiceImpl();
		List<Wenshu> wenshus = wenshuDataService.getAllWenshuData();
		MySQLConnection connection = new MySQLConnectionImpl("wenshu");
		connection.connect();
		for(Wenshu wenshu: wenshus){
			List<String> res0 = connection.query("select keyword from keyword where caseid='"+wenshu.getCaseID()+"';");
			String keywords;
			if(res0.size() == 0){
				keywords = "审理";
			}
			else{
				keywords = res0.get(0);
			}
			String sql = "insert into wenshu value('"+wenshu.getCaseID()+"','"
					+ wenshu.getCaseName() +"','"+wenshu.getCourtName()+"','"
					+ wenshu.getCourtLevel()+"','"+wenshu.getDocumentType()+"','"
					+wenshu.getDocumentName()+"','"+wenshu.getCaseYear()+"','"
					+wenshu.getCaseType()+"','"+wenshu.getCaseProgram()+"','"
					+wenshu.getCaseBrief()+"','"+wenshu.getCaseDate()+"','"
					+wenshu.getLawsString()+"','"+wenshu.getParticipantInfoString()+"','"
					+wenshu.getFullText()+"','"+keywords+"','"+wenshu.getCourtArea()+"');";
			connection.execute(sql);
			System.out.println(wenshu.getCaseID());
		}
		connection.release();
	}
}