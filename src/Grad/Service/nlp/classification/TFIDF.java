package Grad.Service.nlp.classification;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import Grad.Service.dataservice.WenshuDataService;
import Grad.Service.dataservice.impl.WenshuDataServiceImpl;
import Grad.Service.nlp.tool.WordCount;
import Grad.Service.wenshu.Wenshu;

public class TFIDF {
	private List<Wenshu> list;
	private Set<String> wordSet;
	public TFIDF(List<Wenshu> list){
		this.list = list;
		this.wordSet = new HashSet<String>();
		try{
			File file = new File("tmp/word.txt");
			Scanner scanner = new Scanner(file);
			String line = scanner.nextLine();
			String[] e = line.split(",");
			for(int i = 0;i < e.length;i++)
				wordSet.add(e[i]);
			scanner.close();
		} catch(IOException e){
			System.out.println(e.toString());
		}
	}
	/*
	 * 计算一篇文书的TF
	 */
	private Map<String,Double> calculateWordTF(Wenshu wenshu){
//		System.out.println("计算中:"+wenshu.getCaseID());//TODO
		Map<String,Double> map = new HashMap<String,Double>();
		Map<String,Integer> wordCountMap = new WordCount().wordcount(wenshu);
		int size = 0;
		for(String word:this.wordSet){
			if(wordCountMap.containsKey(word)){
				size++;
				double count = (double)wordCountMap.get(word);
				map.put(word, count);
			}
		}
		double wordSize = (double)size;
		Iterator<String> iterator = map.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			double count = map.get(key);
			double tf = 0;
			if(wordSize != 0)
				tf = count/wordSize;
			map.put(key, tf);
//			System.out.println("TF:"+tf);
		}
		return map;
	}
	/*
	 * 计算所有文书的IDF
	 */
	private Map<String,Double> calculateWordIDF(List<Map<String,Double> > tfs){
		Map<String,Double> map = new HashMap<String,Double>();
		double wenshuSize = (double)tfs.size();//表示训练集中文本总数
		Iterator<String> iterator = wordSet.iterator();
		while(iterator.hasNext()){
			String word = iterator.next();
			int wenshuCount = 0;//表示包含该词的文本数
			for(Map<String,Double> wenshuMap:tfs){
				if(wenshuMap.containsKey(word)){
					wenshuCount++;
				}
			}
			double count = (double)wenshuCount;
			double idf = 0;
			if(count != 0){
				idf = Math.log(wenshuSize/count+0.01);
			}
			map.put(word, idf);
		}
		return map;
	}
	/*
	 * 对TF-IDF进行归一化处理
	 */
	private Map<String,Double> calculateTFIDF(Map<String,Double> tfs,Map<String,Double> idfs){
		Map<String,Double> map = new HashMap<String,Double>();
		Map<String,Double> tfidfs = new HashMap<String,Double>();
		double d1 = 0;
		Iterator<String> iterator = idfs.keySet().iterator();
		while(iterator.hasNext()){
			String word = iterator.next();
			double idf = idfs.get(word);
			double tf = 0;
			if(tfs.containsKey(word)){
				tf = tfs.get(word);
			}
			double d0 = idf*tf;
			tfidfs.put(word, d0);
			d1 += d0;
//			System.out.println("D1:"+d1);
		}
		d1 = Math.sqrt(d1);//计算得到分母
		iterator = tfidfs.keySet().iterator();
		while(iterator.hasNext()){
			String word = iterator.next();
			double tfidf0 = tfidfs.get(word);
			double tfidf = tfidf0/d1;
			map.put(word, tfidf);
		}
		return map;
	}
	/*
	 * 一个总的对外接口，用于获得每个文书的tfidf向量
	 */
	public Map<String,Map<String,Double> > calculateTFIDF(){
		Map<String,Map<String,Double> > map = new HashMap<String,Map<String,Double> >();
		List<Map<String,Double> > tfs = new ArrayList<Map<String,Double> >();
		for(Wenshu wenshu:list){
			Map<String,Double> tf = this.calculateWordTF(wenshu);
			tfs.add(tf);
		}
		Map<String,Double> idfs = this.calculateWordIDF(tfs);
		File file = new File("tmp/tfidf.txt");
		FileWriter fw = null;
		BufferedWriter bw = null;
		try{
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
		} catch(IOException e){
			System.err.println(e);
		}
		int size = list.size();
		for(int i = 0;i < size;i++){
			Wenshu wenshu = list.get(i);
			String wenshuID = wenshu.getCaseID();
			String wenshuKeywords = wenshu.getKeywords();
			String wenshuCaseBrief = wenshu.getCaseBrief();
			Map<String,Double> tf = tfs.get(i);
			Map<String,Double> tfidf = this.calculateTFIDF(tf, idfs);
			StringBuilder sb = new StringBuilder();
			Iterator<String> iterator = tfidf.keySet().iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				double value = tfidf.get(key);
				sb.append(value);
				if(iterator.hasNext())
					sb.append(",");
			}
			try {
				bw.write(wenshuID);
				bw.newLine();
				bw.write(wenshuKeywords);
				bw.newLine();
				bw.write(wenshuCaseBrief);
				bw.newLine();
//				bw.write(tfidf.toString());
				bw.write(sb.toString());
				bw.newLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
//			map.put(wenshuID, tfidf);
		}
		try{
			fw.close();
			bw.close();
		} catch(IOException e){
			System.err.println(e);
		}
		list.clear();
		return map;
	}
	public void exec(){
		WordCount wordCount = new WordCount();
		for(Wenshu wenshu:this.list){
			int size = 0;
			Map<String,Integer> map = wordCount.wordcount(wenshu);
			for(String word:this.wordSet){
				if(map.containsKey(word))
					size++;
			}
			System.out.println(size);
		}
	}
	//Test
	public static void main(String[] args){
		WenshuDataService wenshudataservice = new WenshuDataServiceImpl();
		List<Wenshu> wenshuList = wenshudataservice.getSmallTestSet("婚姻纠纷");
		TFIDF tfidf = new TFIDF(wenshuList);
		tfidf.calculateTFIDF();
	}
}
