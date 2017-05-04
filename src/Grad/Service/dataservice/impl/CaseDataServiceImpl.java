package Grad.Service.dataservice.impl;
import java.util.ArrayList;
import java.util.List;
import Grad.Bean.CaseUploadDetail;
import Grad.Service.dataservice.CaseDataService;
import Grad.Service.dataservice.jdbc.MySQLConnection;
import Grad.Service.dataservice.jdbc.MySQLConnectionImpl;
public class CaseDataServiceImpl implements CaseDataService{
	private String path;
	private MySQLConnection connection;
	public CaseDataServiceImpl(){
		this.path = null;
		this.initConnection();
	}
	public CaseDataServiceImpl(String path){
		this.path = path;
		this.initConnection();
	}
	private void initConnection(){
		this.connection = new MySQLConnectionImpl("wenshu");
		this.connection.connect();
	}
	@Override
	public boolean insert(CaseUploadDetail c) {
		String sql = "insert into upload value('"+c.getUsername()+
				"','"+c.getCaseTitle()+"','"+c.getCaseContext()+"','"+c.getUploadDate()+"');";
		return this.connection.execute(sql);
	}
	@Override
	public List<CaseUploadDetail> getCaseUploadDetail(String username) {
		List<CaseUploadDetail> result = new ArrayList<CaseUploadDetail>();
		String sql = "select * from upload where username='"+username+"';";
		List<String> list = this.connection.query(sql);
		int size = list.size();
		for(int i = 0;i < size;i++){
			String line = list.get(i);
			String[] e = line.split(" ");
			CaseUploadDetail detail = new CaseUploadDetail(e[0],e[1],e[2],e[3]);
			result.add(detail);
		}
		return result;
	}
	@Override
	public List<String> getSimilarCases(String caseID) {
		List<String> result = new ArrayList<String>();
		String sql = "select case1,case2,case3,case4,case5 from sim where caseid='"+caseID+"';";
		String line = connection.query(sql).get(0);
		String[] s = line.split(" ");
		for(int i = 0;i < s.length;i++){
			result.add(s[i]);
		}
		return result;
	}
	@Override
	public void deleteCases(String[] list) {
		for(String caseid:list){
			String sql = "delete from upload where casetitle='"+caseid+"';";
			System.out.println(sql);
			connection.execute(sql);
		}
	}
}