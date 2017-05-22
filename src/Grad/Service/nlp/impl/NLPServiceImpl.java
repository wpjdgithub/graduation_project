package Grad.Service.nlp.impl;

import java.util.ArrayList;
import java.util.List;
import Grad.Service.nlp.NLPService;
import Grad.Service.wenshu.Wenshu;
import huaban.analysis.jieba.JiebaSegmenter;

public class NLPServiceImpl implements NLPService{
	private JiebaSegmenter segmenter = new JiebaSegmenter();
	@Override
	public List<String> chineseWordSegmentation(String sentence) {
		List<String> result = segmenter.sentenceProcess(sentence);
		return result;
	}
	@Override
	public List<String> chineseWordSegmentation(Wenshu wenshu) {
		List<String> result = new ArrayList<String>();
		String fullText = wenshu.getFullText();
		String[] sentences = fullText.split("[;；,，.。？?‘’“”\"、:：/【】%+-÷〇…()&○（）—#《》]");
		for(int i = 0;i < sentences.length;i++){
			List<String> words = this.chineseWordSegmentation(sentences[i]);
			for(String word:words){
				result.add(word.trim());
			}
		}
		return result;
	}
	@Override
	public List<String> chineseParaWordSegmentation(String paragraph) {
		List<String> result = new ArrayList<String>();
		String[] sentences = paragraph.split("[;；,，.。？?‘’“”\"、:：/【】%+-÷〇…()&○（）—#《》]");
		for(int i = 0;i < sentences.length;i++){
			List<String> words = this.chineseWordSegmentation(sentences[i]);
			for(String word:words){
				result.add(word.trim());
			}
		}
		return result;
	}
}
