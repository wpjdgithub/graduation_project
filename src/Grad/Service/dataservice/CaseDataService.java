package Grad.Service.dataservice;
import java.util.List;
import Grad.Bean.CaseUploadDetail;
//主要负责上传和下载
public interface CaseDataService {
	public boolean insert(CaseUploadDetail c);
	public List<CaseUploadDetail> getCaseUploadDetail(String username);
	public CaseUploadDetail getUploadedCase(String caseid);
	public List<String> getSimilarCases(String caseID);
	public void deleteCases(String[] list);
	public List<String> getCaseIDsByBrief(String brief);
}
