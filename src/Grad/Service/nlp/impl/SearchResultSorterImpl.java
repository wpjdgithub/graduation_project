package Grad.Service.nlp.impl;

import java.util.List;

import Grad.Service.nlp.SearchResultSorter;
import Grad.Service.nlp.WenshuAnalyzingService;
import Grad.Service.wenshu.Wenshu;

public class SearchResultSorterImpl implements SearchResultSorter{
	
	private WenshuAnalyzingService wenshuAnalyzingService;
	public SearchResultSorterImpl(WenshuAnalyzingService wenshuAnalyzingService){
		this.wenshuAnalyzingService = wenshuAnalyzingService;
	}

	@Override
	public void sort(List<Wenshu> wenshus) {
		// TODO Auto-generated method stub
		
	}

}
