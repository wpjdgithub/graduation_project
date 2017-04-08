package Grad.Service.dataservice.jdbc;

import java.sql.ResultSet;

public interface MySQLConnection {
	public void setTableName(String name);
	public String getTableName();
	public void connect();
	public void release();
	public void execute(String sql);//用于执行插入和删除
	public ResultSet query(String sql);
}
