package Grad.Service.dataservice.jdbc;

import java.util.List;

public interface MySQLConnection {
	public void setTableName(String name);
	public String getTableName();
	public void connect();
	public void release();
	public boolean execute(String sql);//用于执行插入和删除
	public List<String> query(String sql);
}
