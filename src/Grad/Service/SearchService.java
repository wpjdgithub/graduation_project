package Grad.Service;

import java.util.ArrayList;

import Grad.Bean.CaseBrief;
import Grad.Bean.SearchInfo;

public interface SearchService {
	public ArrayList<CaseBrief> search(String input, int pageNum); //一页五个
	public ArrayList<CaseBrief> search(SearchInfo info, int pageNum);//一页五个
}
