package Grad.Service.nlp.tool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Grad.Service.nlp.NLPService;
import Grad.Service.nlp.impl.NLPServiceImpl;
import Grad.Service.wenshu.Wenshu;

public class WordCount {
	private NLPService nlpTool;
	public WordCount(){
		this.nlpTool = new NLPServiceImpl();
	}
	public String wordcount(List<Wenshu> wenshus){
		StringBuilder sb = new StringBuilder();
		Map<String,Integer> countMap = new HashMap<String,Integer>();//用于统计词频
		for(Wenshu wenshu:wenshus){//对输入列表中的每个文书进行处理
			System.out.println(wenshu.getCaseID());
			String fullText = wenshu.getFullText();
			String[] sentences = fullText.split("[;；,，.。？?‘’“”\"、]");
			for(int i = 0;i < sentences.length;i++){
				String sentence = sentences[i];
				if(sentence.length() == 0)//如果是个空串，直接跳过
					continue;
				//分词并进行词频统计
				List<String> words = this.nlpTool.chineseWordSegmentation(sentence);
				for(String word:words){
					if(word.length() == 0 || word.equals(" "))
						continue;
					if(this.isEnglishWord(word) || this.isNumber(word) || this.isStopWords(word))
						continue;
					if(countMap.containsKey(word)){
						int count = countMap.get(word).intValue();
						countMap.put(word, count+1);
					}
					else{
						countMap.put(word, 1);
					}
				}
			}
		}
		System.out.println("词的数量:"+countMap.size());
		//使用插入排序对所有词按照词频进行排序
		LinkedList<String> wordList = new LinkedList<String>();
		LinkedList<Integer> countList = new LinkedList<Integer>();
		Iterator<String> iterator = countMap.keySet().iterator();
		while(iterator.hasNext()){
			System.out.println(wordList.size());
			String key = iterator.next();
			int count = countMap.get(key).intValue();
			if(count < 50)//如果词频小于5，则忽略这个词
				continue;
			int size = countList.size();
			if(size == 0){
				wordList.add(key);
				countList.add(count);
			}
			else{
				int index = 0;
				while(index < size){
					int countAtIndex = countList.get(index);
					if(countAtIndex <= count){
						wordList.add(index, key);
						countList.add(index, count);
						break;
					}
					else{
						index++;
					}
				}
			}
		}
		Iterator<String> iterator1 = wordList.iterator();
		Iterator<Integer> iterator2 = countList.iterator();
		while(iterator1.hasNext()){
			String word = iterator1.next();
			String count = String.valueOf(iterator2.next().intValue());
			sb.append(word+":"+count+";");
		}
		//获取结果并去除尾部多余的";"
		String result = sb.toString();
		int resultLength = result.length();
		result = result.substring(0,resultLength-1);
		return result;
	}
	public String wordcount(Wenshu wenshu){
		return null;
	}
	private boolean isNumber(String s){
		boolean isInteger = this.isInteger(s);
		boolean isFloat = this.isFloat(s);
		if(isInteger || isFloat){
			return true;
		}
		else{
			return false;
		}
	}
	private boolean isInteger(String s){
		try{
			Integer.parseInt(s);
		} catch (Exception e){
			return false;
		}
		return true;
	}
	private boolean isFloat(String s){
		try{
			Double.parseDouble(s);
		} catch(Exception e){
			return false;
		}
		return true;
	}
	private boolean isEnglishWord(String s){
		int length = s.length();
		for(int i = 0;i < length;i++){
			char c = s.charAt(i);
			if(!this.isEnglishCharacter(c))
				return false;
		}
		return true;
	}
	private boolean isEnglishCharacter(char c){
		if((c <= 'z' && c >= 'a')||(c <= 'Z' && c >= 'A')){
			return true;
		}
		else{
			return false;
		}
	}
	private boolean isStopWords(String s){
		return StopWords.isStopWords(s);
	}
}
