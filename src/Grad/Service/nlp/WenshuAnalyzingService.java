package Grad.Service.nlp;

import java.util.List;

import Grad.Service.wenshu.Wenshu;

public interface WenshuAnalyzingService {
	public List<String> calculateKeywords(Wenshu wenshu);
	public List<String> calculateKeywords(Wenshu wenshu,String input);
}
