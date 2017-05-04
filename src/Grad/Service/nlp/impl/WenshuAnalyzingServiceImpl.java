package Grad.Service.nlp.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import Grad.Service.nlp.NLPService;
import Grad.Service.nlp.WenshuAnalyzingService;
import Grad.Service.wenshu.Wenshu;
import tfidf.TF_IDF;
import tfidf.WenshuWords;

public class WenshuAnalyzingServiceImpl implements WenshuAnalyzingService{
	
	public WenshuAnalyzingServiceImpl(){
		
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

	@Override
	public Map<String, Double> calculateTFIDF(Wenshu wenshu) {
		Map<String,Double> res = new HashMap<String,Double>();
		Map<String,Double> idfMap = TF_IDF.loadIDF();
		NLPService nlpService = new NLPServiceImpl();
		List<String> words = nlpService.chineseWordSegmentation(wenshu);
		WenshuWords wenshuWords = new WenshuWords(wenshu.getCaseID(),wenshu.getCaseBrief(),words);
		Map<String,Double> tfMap = new TF_IDF().calculateTF(wenshuWords);
		Iterator<String> iter = tfMap.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next();
			if(idfMap.containsKey(key)){
				double idf = idfMap.get(key);
				double tf = tfMap.get(key);
				double tfidf = tf*idf;
				res.put(key, tfidf);
			}
		}
		return res;
	}

}
