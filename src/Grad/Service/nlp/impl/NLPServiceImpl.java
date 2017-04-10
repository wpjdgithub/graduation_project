package Grad.Service.nlp.impl;

import java.util.List;

import Grad.Service.nlp.NLPService;
import huaban.analysis.jieba.JiebaSegmenter;

public class NLPServiceImpl implements NLPService{
	private JiebaSegmenter segmenter = new JiebaSegmenter();
	@Override
	public List<String> chineseWordSegmentation(String sentence) {
		List<String> result = segmenter.sentenceProcess(sentence);
		return result;
	}
	
	//Test
	public static void main(String[] args){
		NLPService nlpservice = new NLPServiceImpl();
		String input = "王尼玛是暴走大事件这个节目的主持人";
		List<String> result = nlpservice.chineseWordSegmentation(input);
		System.out.println(result);
	}

}
