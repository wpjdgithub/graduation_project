package Grad.Service.dataservice.jdbc;

import java.util.ArrayList;
import java.util.List;

public class MySQLConnectionPool implements MySQLConnectionPoolService{
	private boolean isActive = false;//连接池活动状态
	private int countActive = 0;//记录创建的总连接数
	//空闲连接
	private List<MySQLConnection> freeConnection = new ArrayList<MySQLConnection>();
	//活动连接
	private List<MySQLConnection> activeConnection = new ArrayList<MySQLConnection>();
	//将线程与连接绑定，保证事务能统一执行
	private static ThreadLocal<MySQLConnection> threadLocal = new ThreadLocal<MySQLConnection>();
	public void init(){
		//初始化
	}
	@Override
	public synchronized MySQLConnection getConnection() {
		//获取连接
		return null;
	}

	@Override
	public MySQLConnection getCurrentConnection() {
		//获取当前连接
		return null;
	}

	@Override
	public void releaseConnection(MySQLConnection connection) {
		//释放连接
	}

	@Override
	public void destory() {
		//销毁连接池
	}
	@Override
	public boolean isActive() {
		//获取连接池状态
		return false;
	}

	@Override
	public void checkPool() {
		//定时检查连接池情况
	}

}
