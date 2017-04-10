package Grad.Service.nlp.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Grad.Service.dataservice.WenshuDataService;
import Grad.Service.dataservice.impl.WenshuDataServiceImpl;
import Grad.Service.nlp.NLPService;
import Grad.Service.nlp.impl.NLPServiceImpl;
import Grad.Service.wenshu.Wenshu;

public class WordCount {
	private NLPService nlpTool;
	public WordCount(){
		this.nlpTool = new NLPServiceImpl();
	}
	public Map<String,Integer> wordcount(List<Wenshu> wenshus){
		Map<String,Integer> result = new HashMap<String,Integer>();
		for(Wenshu wenshu:wenshus){
			Map<String,Integer> map = this.wordcount(wenshu);
			Iterator<String> iterator = map.keySet().iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				if(result.containsKey(key)){
					int count = result.get(key)+map.get(key);
					result.put(key, count);
				}
				else{
					result.put(key, map.get(key));
				}
			}
		}
		return result;
	}
	public Map<String,Integer> wordcount(Wenshu wenshu){
		Map<String,Integer> map = new HashMap<String,Integer>();
		List<String> words = this.nlpTool.chineseWordSegmentation(wenshu);
		for(String word:words){
			if(this.isStopWords(word) ||this.containsOthers(word) 
					||this.startWithUseless(word))
				continue;
			if(map.containsKey(word)){
				int count = map.get(word).intValue()+1;
				map.put(word,count);
			}
			else{
				map.put(word, 1);
			}
		}
		return map;
	}
	public Set<String> getAllWords(List<Wenshu> list){
		Map<String,Integer> wordcount = this.wordcount(list);
		Set<String> set = new HashSet<String>();
		Iterator<String> iterator = wordcount.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			if(key.length() < 2)
				continue;
			int count = wordcount.get(key);
			if(count < 150)
				continue;
			set.add(key);
			System.out.println(key+" "+count);
		}
		return set;
	}
	//将词提取出来存到一个文件里
	public static void main(String[] args) throws IOException{
		WenshuDataService dataservice = new WenshuDataServiceImpl();
		List<Wenshu> list = dataservice.getAllWenshuData();
		Set<String> set = new WordCount().getAllWords(list);
		File file = new File("tmp/word.txt");
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		Iterator<String> iterator = set.iterator();
		while(iterator.hasNext()){
			String word = iterator.next();
			bw.write(word);
			if(iterator.hasNext())
				bw.write(",");
		}
		bw.newLine();
		bw.close();
		fw.close();
		System.out.println(set.size());
	}
	private boolean isEnglishCharacter(char c){
		if((c <= 'z' && c >= 'a')||(c <= 'Z' && c >= 'A')){
			return true;
		}
		else{
			return false;
		}
	}
	private boolean isDigit(char c){
		if(c <= '9' && c >= '0'){
			return true;
		}
		else{
			return false;
		}
	}
	private boolean isStopWords(String s){
		return StopWords.isStopWords(s);
	}
	private boolean containsOthers(String s){
		int length = s.length();
		for(int i = 0;i < length;i++){
			char c = s.charAt(i);
			if(this.isDigit(c) || this.isEnglishCharacter(c)
					||c == '\t' || c == '\n' || c == ' ')
				return true;
		}
		return false;
	}
	private boolean startWithUseless(String s){
		if(s.startsWith("第") || s.startsWith("一") || s.startsWith("二")
				|| s.startsWith("三") || s.startsWith("四")
				|| s.startsWith("五") || s.startsWith("六")
				|| s.startsWith("七") || s.startsWith("八")
				|| s.startsWith("九") || s.startsWith("十"))
			return true;
		else
			return false;
	}
}
