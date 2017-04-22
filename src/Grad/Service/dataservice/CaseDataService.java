package Grad.Service.dataservice;
import java.util.List;
import Grad.Bean.CaseUploadDetail;
//主要负责上传和下载
public interface CaseDataService {
	public boolean insert(CaseUploadDetail c);
	public List<CaseUploadDetail> getCaseUploadDetail(String username);
}
