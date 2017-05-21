package Grad.Service;

import java.util.List;

import Grad.Bean.CaseCompare;
import Grad.Bean.CaseMinMes;
import Grad.Bean.UserInfo;

public interface UserSerivice {
	public boolean register(UserInfo info);
	public boolean login(UserInfo info);
	public UserInfo getInfo(String username);
	public List<CaseMinMes> getMinMes(String username);
	
	public List<CaseCompare> getCompareMes(String username, String id, int type);
	// type为1时  依据用户名和该用户上传的一个文书，查询这个文书与其他文书之间的相似度 
	// type为2时  依据这2个信息查询系统文书与该文书的相似案例，并将其他文书与搜索到的相关案例进行检测
}
