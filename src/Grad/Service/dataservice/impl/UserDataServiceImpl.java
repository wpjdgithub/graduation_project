package Grad.Service.dataservice.impl;
import java.util.ArrayList;
import java.util.List;
import Grad.Bean.UserInfo;
import Grad.Service.dataservice.UserDataService;
import Grad.Service.dataservice.jdbc.MySQLConnection;
import Grad.Service.dataservice.jdbc.MySQLConnectionImpl;

public class UserDataServiceImpl implements UserDataService {
	
	private MySQLConnection connection;
	
	public UserDataServiceImpl(){
		this.connection = new MySQLConnectionImpl("wenshu");
		this.connection.connect();
	}
	
	public List<String> getCaseMinMes(String username){
		List<String> res = this.connection.query("select count,casetitle,uploaddate from upload where username='"+username+"';");
		return res;
	}

	@Override
	public UserInfo getUserInfo(String username) {
		List<String> list = this.connection.query("select * from user where username='"+username+"';");
		if(list.size() == 0){
			return null;
		}
		else{
			String line = list.get(0);
			String[] e = line.split(" ");
			UserInfo result = new UserInfo(e[0],e[1]);
			result.setPassword(e[2]);
			return result;
		}
	}

	@Override
	public boolean insert(UserInfo userInfo) {
		String sql = "insert into user value('"+userInfo.getUsername()+"','"+userInfo.getName()+"','"+userInfo.getPassword()+"');";
		return this.connection.execute(sql);
	}

	@Override
	public boolean remove(UserInfo userInfo) {
		String sql = "delete from user where username='"+userInfo.getUsername()+"';";
		return this.connection.execute(sql);
	}

	@Override
	public boolean update(UserInfo userInfo) {
		String sql = "update user set name='"+userInfo.getName()+"',password='"+userInfo.getPassword()+"' where username='"+userInfo.getUsername()+"';";
		return this.connection.execute(sql);
	}

	@Override
	public void close() {
		this.connection.release();
	}

}
