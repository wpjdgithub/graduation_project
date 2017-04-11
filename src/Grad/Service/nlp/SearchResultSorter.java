package Grad.Service.nlp;

import java.util.List;

import Grad.Service.wenshu.Wenshu;

public interface SearchResultSorter {
	public void sort(List<Wenshu> wenshus);
}
