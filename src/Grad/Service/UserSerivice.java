package Grad.Service;

import Grad.Bean.UserInfo;

public interface UserSerivice {
	public boolean register(UserInfo info);
	public boolean login(UserInfo info);
	public UserInfo getInfo(String username);
}
