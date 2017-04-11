package Grad.Service.nlp;

import Grad.Service.nlp.impl.NLPServiceImpl;
import Grad.Service.nlp.impl.SearchResultSorterImpl;
import Grad.Service.nlp.impl.WenshuAnalyzingServiceImpl;
import Grad.Service.nlp.tool.TFIDF;

public class NLPFactory {
	private static TFIDF tfidf;
	private static NLPService nlpService;
	private static WenshuAnalyzingService wenshuAnalyzingService;
	private static SearchResultSorter searchResultSorter;
	static{
		tfidf = new TFIDF(null);
		tfidf.loadIDF();
		tfidf.loadTFIDF();
		nlpService = new NLPServiceImpl();
		wenshuAnalyzingService = new WenshuAnalyzingServiceImpl(tfidf);
		searchResultSorter = new SearchResultSorterImpl(wenshuAnalyzingService);
	}
	public static TFIDF getTFIDFService(){
		return tfidf;
	}
	public static NLPService getNLPService(){
		return nlpService;
	}
	public static WenshuAnalyzingService getWenshuAnalyzingService(){
		return wenshuAnalyzingService;
	}
	public static SearchResultSorter getSearchResultSorterService(){
		return searchResultSorter;
	}
}
