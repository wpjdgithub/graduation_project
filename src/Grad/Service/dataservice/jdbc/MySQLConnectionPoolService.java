package Grad.Service.dataservice.jdbc;

public interface MySQLConnectionPoolService {
	//获得连接
	public MySQLConnection getConnection();
	//获得当前连接
	public MySQLConnection getCurrentConnection();
	//回收连接
	public void releaseConnection(MySQLConnection connection);
	//销毁清空
	public void destory();
	//连接池是活动状态
	public boolean isActive();
	//定时器，检查连接池
	public void checkPool();
}
