package Grad.Service.dataservice.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLConnectionImpl implements MySQLConnection{
	
	public static void main(String[] args){
		MySQLConnection mysqlConnection = new MySQLConnectionImpl("testsql");
		mysqlConnection.connect();
	}
	
	private String tableName;
	private Connection connection;
	
	public MySQLConnectionImpl(){
		this.tableName = null;
		this.connection = null;
	}
	
	public MySQLConnectionImpl(String tableName){
		this.tableName = tableName;
		this.connection = null;
	}

	@Override
	public void setTableName(String name) {
		this.tableName = name;
	}

	@Override
	public String getTableName() {
		return this.tableName;
	}

	@Override
	public void connect() {
		if(this.tableName == null){
			System.out.println("没有对应的数据库名!连接失败!");
			return;
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("成功加载MySQL驱动!");
		} catch (ClassNotFoundException e) {
			System.out.println("找不到MySQL驱动!");
			e.printStackTrace();
		}
		String url = "jdbc:mysql://127.0.0.1:3306/"+this.tableName+"?serverTimezone=GMT%2B8";
		try {
			this.connection = DriverManager.getConnection(url,"root","123456");
			System.out.println("成功连接到数据库!");
		} catch (SQLException e) {
			e.printStackTrace();
			this.connection = null;
			System.out.println("连接失败!");
		}
		
	}

	@Override
	public void release() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.connection = null;
	}

	@Override
	public boolean execute(String sql) {
		if(this.connection == null){
			return false;
		}
		try {
			Statement statement = connection.createStatement();
			statement.execute(sql);
			statement.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<String> query(String sql) {
		if(this.connection == null){
			return null;
		}
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			List<String> result = new ArrayList<String>();
			while(resultSet.next()){
				StringBuilder sb = new StringBuilder();
				int size = resultSet.getMetaData().getColumnCount();
				for(int i = 0;i < size;i++){
					sb.append(resultSet.getString(i+1));
					sb.append(" ");
				}
				String line = sb.toString().trim();
				result.add(line);
			}
			statement.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
