package Grad.Service;

import java.util.List;

import Grad.Bean.CaseMinMes;
import Grad.Bean.UserInfo;

public interface UserSerivice {
	public boolean register(UserInfo info);
	public boolean login(UserInfo info);
	public UserInfo getInfo(String username);
	public List<CaseMinMes> getMinMes(String username);
}
