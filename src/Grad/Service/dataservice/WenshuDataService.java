package Grad.Service.dataservice;

import java.util.List;

import Grad.Service.wenshu.Wenshu;

public interface WenshuDataService {
	public List<Wenshu> getAllWenshuData();
	public List<Wenshu> getSmallTestSet();
}
