package Grad.Service.nlp;

import java.util.List;

import Grad.Service.wenshu.Wenshu;

public interface NLPService {
	public List<String> chineseWordSegmentation(String sentence);
	public List<String> chineseWordSegmentation(Wenshu wenshu);
}
