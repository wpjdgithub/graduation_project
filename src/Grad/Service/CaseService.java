package Grad.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import Grad.Bean.CaseDetail;
import Grad.Bean.CaseJudgeCompare;
import Grad.Bean.CaseMinMes;

public interface CaseService {
	public CaseDetail uploadCase(String username, String filename, InputStream in);
	public ArrayList<CaseMinMes> getCaseByUser(String username); // 获取用户个人上传文书
	public CaseDetail getCaseByTitle(String id); // 依据文书id获取全部信息
	public boolean deleteCase(String[] list); // 语句用户个人上传文书的id删除文书
	
	public List<CaseJudgeCompare> getJudgeMes(String id);// 依据文书id获取相关判决情况
}
