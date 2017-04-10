package Grad.Service.nlp.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import Grad.Service.nlp.NLPService;
import Grad.Service.nlp.impl.NLPServiceImpl;
import Grad.Service.wenshu.Wenshu;
/**
 * 一个用于计算TFIDF的工具类
 * @author yd1996
 */
public class TFIDF {
	private NLPService nlpservice;
	private WordCounter wordCounter;
	private Set<String> wordSet;
	private List<Wenshu> wenshus;
	private Map<String,Double> idfMap = null;
	private Map<String,Map<String,Double> > tfidfMap = null;
	public TFIDF(List<Wenshu> wenshus){
		this.nlpservice = new NLPServiceImpl();
		this.wordCounter = new WordCounter();
		this.wordSet = this.wordCounter.load();
		this.wenshus = wenshus;
	}
	public double getIDFByWord(String word){
		double result = this.idfMap.get(word);
		return result;
	}
	public Map<String,Double> getTFIDFByCaseID(String caseID){
		Map<String,Double> result = this.tfidfMap.get(caseID);
		return result;
	}
	public Map<String,Double> getIDFMap(){
		return this.idfMap;
	}
	public Map<String,Map<String,Double> > getTFIDFMap(){
		return this.tfidfMap;
	}
	//用于计算IDF
	public Map<String,Double> calculateIDF(){
		Map<String,Double> result = new HashMap<String,Double>();
		Iterator<String> iterator = this.wordSet.iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			result.put(key, 1.0);
		}
		int wenshuSize = this.wenshus.size();
		double wenshuCount = (double)wenshuSize;//语料库的文档总数
		for(int i = 0;i < wenshuSize;i++){
			Wenshu wenshu = this.wenshus.get(i);
			String fullText = wenshu.getFullText();
			iterator = result.keySet().iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				if(fullText.contains(key)){
					double count = result.get(key)+1.0;
					result.put(key, count);
					System.out.println(wenshu.getCaseID()+" "+key+" "+count);
				}
			}
		}
		iterator = result.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			double count = result.get(key);
			double idf = Math.log(wenshuCount/count);
			result.put(key, idf);
		}
		this.idfMap = result;
		return result;
	}
	public void saveIDF(){
		File file = new File("tmp/idf.txt");
		try{
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			Iterator<String> iterator = this.idfMap.keySet().iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				double idf = this.idfMap.get(key);
				bw.write(key+"="+idf);
				if(iterator.hasNext()){
					bw.write(",");
				}
			}
			bw.newLine();
			bw.close();
			fw.close();
		} catch (IOException e){
			System.out.println("IDF保存失败");
		}
	}
	public void loadIDF(){
		this.idfMap = new HashMap<String,Double>();
		File file = new File("tmp/idf.txt");
		try{
			Scanner scanner = new Scanner(file);
			String line = scanner.nextLine();
			String[] e = line.split(",");
			for(int i = 0;i < e.length;i++){
				String[] s = e[i].split("=");
				String key = s[0];
				double value = Double.parseDouble(s[1]);
				this.idfMap.put(key, value);
			}
			scanner.close();
		} catch (IOException e){
			System.out.println("IDF加载失败!");
		}
	}
	public Map<String,Double> calculateTFIDF(Wenshu wenshu){
		Map<String,Double> result = new HashMap<String,Double>();
		Map<String,Integer> wordcount = this.wordCounter.wordcount(wenshu);
		List<String> wordList = this.nlpservice.chineseWordSegmentation(wenshu);
		int wordSize = wordList.size();
		double wordCount = (double)wordSize;
		Iterator<String> iterator = wordcount.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			double count = wordcount.get(key);
			double tf = count/wordCount;
			double idf = this.idfMap.get(key).doubleValue();
			double tfidf = tf*idf;
			result.put(key, tfidf);
		}
		return result;
	}
	public Map<String,Map<String,Double> > calculateTFIDF(){
		Map<String,Map<String,Double> > result = 
				new HashMap<String,Map<String,Double> >();
		int wenshuSize = this.wenshus.size();
		for(int i = 0;i < wenshuSize;i++){
			Wenshu wenshu = wenshus.get(i);
			Map<String,Double> map = this.calculateTFIDF(wenshu);
			String caseID = wenshu.getCaseID();
			System.out.println(caseID);
			result.put(caseID, map);
		}
		this.tfidfMap = result;
		return result;
	}
	public void saveTFIDF(){
		File file = new File("tmp/tfidf.txt");
		try{
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			Iterator<String> idIterator = this.tfidfMap.keySet().iterator();
			while(idIterator.hasNext()){
				String caseID = idIterator.next();
				bw.write(caseID);
				bw.newLine();
				Map<String,Double> map = this.tfidfMap.get(caseID);
				Iterator<String> iterator = map.keySet().iterator();
				while(iterator.hasNext()){
					String key = iterator.next();
					double tfidf = map.get(key);
					bw.write(key+"="+tfidf);
					if(iterator.hasNext()){
						bw.write(",");
					}
				}
				bw.newLine();
			}
			bw.close();
			fw.close();
		} catch(IOException e){
			System.out.println("保存TF-IDF失败!");
		}
	}
	public void loadTFIDF(){
		this.tfidfMap = new HashMap<String,Map<String,Double> >();
		File file = new File("tmp/tfidf.txt");
		try{
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				String caseID = scanner.nextLine();
				System.out.println(caseID);
				Map<String,Double> map = new HashMap<String,Double>();
				String line = scanner.nextLine();
				String[] pair = line.split(",");
				for(int i = 0;i < pair.length;i++){
					String[] e = pair[i].split("=");
					String key = e[0];
					double value = Double.parseDouble(e[1]);
					map.put(key, value);
				}
				this.tfidfMap.put(caseID, map);
			}
			scanner.close();
		} catch (IOException e){
			System.out.println("TF-IDF加载失败");
		}
	}
}
