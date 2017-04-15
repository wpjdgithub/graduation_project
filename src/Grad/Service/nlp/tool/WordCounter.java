package Grad.Service.nlp.tool;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import Grad.Service.nlp.NLPService;
import Grad.Service.nlp.impl.NLPServiceImpl;
import Grad.Service.wenshu.Wenshu;
/**
 * 一个主要用于文书词频统计的工具类
 * @author yd1996
 */
public class WordCounter {
	private NLPService nlpService;
	private String path = "F:/Programming.Project/data/";
	public WordCounter(){
		this.nlpService = new NLPServiceImpl();
	}
	public void setPath(String path){
		this.path = path;
	}
	//对一个文书进行词频统计
	public Map<String,Integer> wordcount(Wenshu wenshu){
		Map<String,Integer> result = new HashMap<String,Integer>();
		List<String> words = this.nlpService.chineseWordSegmentation(wenshu);
		int size = words.size();
		for(int i = 0;i < size;i++){
			String word = words.get(i);
			if(word.length() < 2 || this.isStopWords(word)
					|| this.containOthers(word) || this.startsWithUseLess(word)){
				continue;
			}
			else{
				if(result.containsKey(word)){
					int count = result.get(word)+1;
					result.put(word, count);
				}
				else{
					result.put(word, 1);
				}
			}
		}
		return result;
	}
	//对一系列文书进行词频统计
	public Map<String,Integer> wordcount(List<Wenshu> wenshus){
		Map<String,Integer> result = new HashMap<String,Integer>();
		int size = wenshus.size();
		for(int i = 0;i < size;i++){
			Wenshu wenshu = wenshus.get(i);
			List<String> words = this.nlpService.chineseWordSegmentation(wenshu);
			int wordSize = words.size();
			for(int j = 0;j < wordSize;j++){
				String word = words.get(j);
				if(word.length() < 2 || this.isStopWords(word)
					|| this.containOthers(word) || this.startsWithUseLess(word)){
					continue;
				}
				else{
					if(result.containsKey(word)){
						int count = result.get(word)+1;
						result.put(word, count);
					}
					else{
						result.put(word, 1);
					}
				}
			}
		}
		return result;
	}
	//用于获取所有文书中的所有词汇组成的集合
	public Set<String> getWordSet(List<Wenshu> wenshus){
		Set<String> result = new HashSet<String>();
		int size = wenshus.size();
		for(int i = 0;i < size;i++){
			Wenshu wenshu = wenshus.get(i);
			List<String> words = this.nlpService.chineseWordSegmentation(wenshu);
			int wordSize = words.size();
			for(int j = 0;j < wordSize;j++){
				String word = words.get(j);
				if(word.length() < 2 || this.isStopWords(word)
						|| this.containOthers(word) || this.startsWithUseLess(word)){
					continue;
				}
				else{
					result.add(word);
				}
			}
		}
		return result;
	}
	//用于保存统计好的词汇集合
	public void save(Set<String> wordSet){
		File file = new File(this.path+"tmp/wordset.txt");
		try{
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			Iterator<String> iterator = wordSet.iterator();
			while(iterator.hasNext()){
				String word = iterator.next();
				bw.write(word);
				if(iterator.hasNext()){
					bw.write(",");
				}
			}
			bw.close();
			fw.close();
		} catch(IOException e){
			System.out.println("单词存储出现异常!");
		}
	}
	//用于加载保存在文件里面的词汇集合
	public Set<String> load(){
		Set<String> result = new HashSet<String>();
		File file = new File(this.path+"tmp/wordset.txt");
		try{
			Scanner scanner = new Scanner(file);
			String line = scanner.nextLine();
			String[] word = line.split(",");
			for(int i = 0;i < word.length;i++){
				result.add(word[i]);
			}
			scanner.close();
		} catch(IOException e){
			System.out.println("无法加载相关单词集合!");
		}
		return result;
	}
	private boolean isStopWords(String word){//判断是否是停用词
		boolean result = StopWords.isStopWords(word);
		return result;
	}
	private boolean containOthers(String word){//是否包含英文字母、数字、奇怪的符号
		int length = word.length();
		for(int i = 0;i < length;i++){
			char c = word.charAt(i);
			if(this.isDigit(c) || this.isEnglishCharacter(c) 
					|| this.isSomethingStrange(c)){
				return true;
			}
		}
		return false;
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
	private boolean isSomethingStrange(char c){
		if(c == '\n' || c == '\t' || c == ' '){
			return true;
		}
		else{
			return false;
		}
	}
	private boolean startsWithUseLess(String word){
		if(word.startsWith("第") || word.startsWith("一") || word.startsWith("二")
				|| word.startsWith("三") || word.startsWith("四")
				|| word.startsWith("五") || word.startsWith("六")
				|| word.startsWith("七") || word.startsWith("八")
				|| word.startsWith("九") || word.startsWith("十")){
			return true;
		}
		else{
			return false;
		}
	}
}
