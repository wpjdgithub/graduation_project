package Grad.Service.nlp;

import Grad.Service.nlp.impl.NLPServiceImpl;
import Grad.Service.nlp.impl.WenshuAnalyzingServiceImpl;
import Grad.Service.nlp.tool.TFIDF;

public class NLPFactory {
	private static TFIDF tfidf;
	private static NLPService nlpService;
	private static WenshuAnalyzingService wenshuAnalyzingService;
	static{
		tfidf = new TFIDF(null);
		tfidf.loadIDF();
		tfidf.loadTFIDF();
		nlpService = new NLPServiceImpl();
		wenshuAnalyzingService = new WenshuAnalyzingServiceImpl();
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
}
