package Grad.Service;

import Grad.Bean.CaseFilter;
import Grad.Bean.CaseSearchRes;
import Grad.Bean.SearchInfo;

public interface SearchService {
	public CaseSearchRes search(String input); //一页五个
	public CaseSearchRes search(SearchInfo info);//一页五个
	public CaseSearchRes search(SearchInfo info, CaseFilter filter);
	public CaseSearchRes search(CaseSearchRes res0,CaseFilter filter);
}
