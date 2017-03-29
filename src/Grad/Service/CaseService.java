package Grad.Service;

import java.io.InputStream;
import java.util.ArrayList;

import Grad.Bean.CaseDetail;
import Grad.Bean.CaseMinMes;

public interface CaseService {
	public boolean uploadCase(String username, InputStream in);
	public ArrayList<CaseMinMes> getCaseByUser(String username); // 获取用户个人上传文书
	
	public CaseDetail getCaseByTitle(String id); // 依据文书id获取全部信息
}
