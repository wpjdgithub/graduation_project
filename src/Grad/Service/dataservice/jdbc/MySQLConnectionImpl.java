package Grad.Service.dataservice.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnectionImpl implements MySQLConnection{
	
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
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("成功加载MySQL驱动!");
		} catch (ClassNotFoundException e) {
			System.out.println("找不到MySQL驱动!");
			e.printStackTrace();
		}
		String url = "jdbc:mysql://localhost:3306/"+this.tableName;
		try {
			this.connection = DriverManager.getConnection(url,"root","123");
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
	public void execute(String sql) {
		if(this.connection == null){
			return;
		}
		try {
			Statement statement = connection.createStatement();
			statement.execute(sql);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ResultSet query(String sql) {
		if(this.connection == null){
			return null;
		}
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			statement.close();
			return resultSet;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
