package Grad.Service.nlp.impl;

import java.util.List;

import Grad.Service.nlp.WenshuAnalyzingService;
import Grad.Service.nlp.tool.TFIDF;
import Grad.Service.wenshu.Wenshu;

public class WenshuAnalyzingServiceImpl implements WenshuAnalyzingService{
	
	private TFIDF tfidf;
	public WenshuAnalyzingServiceImpl(TFIDF tfidf){
		this.tfidf = tfidf;
	}

	@Override
	public List<String> calculateKeywords(Wenshu wenshu) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> calculateKeywords(Wenshu wenshu, String input) {
		// TODO Auto-generated method stub
		return null;
	}

}
