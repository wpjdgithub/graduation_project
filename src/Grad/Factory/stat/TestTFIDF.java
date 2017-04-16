package Grad.Factory.stat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Grad.Service.nlp.tool.TFIDF;

public class TestTFIDF {
	public static void count(TFIDF tfidf){
		Map<String,Map<String,Double> > map = tfidf.getTFIDFMap();
		int wenshuCount = map.size();
		int minWordCount = 100000;
		int maxWordCount = 0;
		int sumWordCount = 0;
		double avgWordCount = 0;
		Iterator<String> iterator = map.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			Map<String,Double> wenshuWords = map.get(key);
			int size = wenshuWords.size();
			if(size < minWordCount){
				minWordCount = size;
			}
			if(size > maxWordCount){
				maxWordCount = size;
			}
			sumWordCount += size;
		}
		avgWordCount = ((double)sumWordCount)/((double)wenshuCount);
		System.out.println("文书总数:"+wenshuCount);
		System.out.println("最少词数:"+minWordCount);
		System.out.println("最多词数:"+maxWordCount);
		System.out.println("平均词数:"+avgWordCount);
		/*
		 * 文书总数:23422
		 * 最少词数:10
		 * 最多词数:991
		 * 平均词数:185.57983946716763
		 * 所以每篇文章抽取十个关键词
		 */
	}
	public static void miningKeywords(TFIDF tfidf) throws IOException{
		Map<String,Map<String,Double> > map = tfidf.getTFIDFMap();
		File file = new File("F:\\Programming.Project\\data\\stat.txt");
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		Iterator<String> iterator = map.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			Map<String,Double> statMap = map.get(key);
			List<Map.Entry<String, Double> > entrys =
					new ArrayList<Map.Entry<String,Double> >(statMap.entrySet());
			Collections.sort(entrys,new Comparator<Map.Entry<String,Double> >(){
				@Override
				public int compare(Entry<String, Double> arg0, Entry<String, Double> arg1) {
					double value0 = arg0.getValue();
					double value1 = arg1.getValue();
					if(value0 > value1)
						return 1;
					else if(value0 < value1)
						return -1;
					else
						return 0;
				}
				
			});
			StringBuilder sb = new StringBuilder();
			sb.append(key+" ");
			for(int i = 0;i < 10;i++){
				String word = entrys.get(i).getKey();
				double tfidfValue = entrys.get(i).getValue();
				sb.append(word+"="+tfidfValue);
				if(i != 9)
					sb.append(" ");
			}
			String result = sb.toString();
			bw.write(result);
			bw.newLine();
			System.out.println(result);
		}
		bw.close();
		fw.close();
	}
	public static void removeWordsWithHighIDF(TFIDF tfidf) throws IOException{
		Map<String,Double> map = tfidf.getIDFMap();
		List<Map.Entry<String, Double> > list = 
				new ArrayList<Map.Entry<String,Double> >(map.entrySet());
		Collections.sort(list,new Comparator<Map.Entry<String, Double> >(){
			@Override
			public int compare(Entry<String, Double> arg0, Entry<String, Double> arg1) {
				double value0 = arg0.getValue();
				double value1 = arg1.getValue();
				if(value0 > value1)
					return 1;
				else if(value0 < value1)
					return -1;
				else
					return 0;
			}
		});
		int size = list.size();
		File file = new File("F:\\Programming.Project\\data\\highfrequency.txt");
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		for(int i = 0;i < size;i++){
			String key = list.get(i).getKey();
			double idf = list.get(i).getValue();
			if(idf > 0.99)
				continue;
			bw.write(key);
			bw.newLine();
		}
		bw.close();
		fw.close();
	}
	public static void main(String[] args){
		TFIDF tfidf = new TFIDF();
		tfidf.loadIDF();
//		tfidf.loadTFIDF();
		try {
			removeWordsWithHighIDF(tfidf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
