package Grad.Service.dataservice;

import java.util.List;

import Grad.Bean.UserInfo;

public interface UserDataService {
	public UserInfo getUserInfo(String username);
	public boolean insert(UserInfo userInfo);
	public boolean remove(UserInfo userInfo);
	public boolean update(UserInfo userInfo);
	public void close();
	public List<String> getCaseMinMes(String username);
}
