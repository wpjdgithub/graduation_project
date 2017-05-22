package tfidf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Grad.Service.dataservice.jdbc.MySQLConnection;
import Grad.Service.dataservice.jdbc.MySQLConnectionImpl;

public class TF_IDF {
	private WordCount wordcount;
	public TF_IDF(){
		this.wordcount = new WordCount();
	}
	public Map<String,Double> calculateIDF(List<WenshuWords> wenshus){
		Map<String,Double> res = new HashMap<String,Double>();
		double sum = (double)wenshus.size();
		for(WenshuWords wenshu: wenshus){
			Map<String,Integer> countMap = this.wordcount.wordcount(wenshu.getWords());
			Iterator<String> iterator = countMap.keySet().iterator();
			while(iterator.hasNext()){
				String word = iterator.next();
				if(res.containsKey(word)){
					double count = res.get(word)+1;
					res.put(word, count);
				}
				else{
					res.put(word, 1.0);
				}
			}
		}
		Iterator<String> keyIter = res.keySet().iterator();
		while(keyIter.hasNext()){
			String key = keyIter.next();
			double idf = Math.log(sum/res.get(key)+0.01);
			res.put(key, idf);
		}
		return res;
	}
	public Map<String,Double> calculateTF(WenshuWords wenshu){
		Map<String,Double> res = new HashMap<String,Double>();
		Map<String,Integer> countMap = this.wordcount.wordcount(wenshu.getWords());
		double sum = (double)wenshu.getWords().size();
		Iterator<String> iterator = countMap.keySet().iterator();
		while(iterator.hasNext()){
			String word = iterator.next();
			double count = (double)countMap.get(word);
			double tf = count/sum;
			res.put(word, tf);
		}
		return res;
	}
	public Map<String,Map<String,Double>> calculateTFIDF(Map<String,Double> idfs,Map<String,Map<String,Double>> tfs){
		Map<String,Map<String,Double> > res = new HashMap<String,Map<String,Double>>();
		Iterator<String> iterator = tfs.keySet().iterator();
		while(iterator.hasNext()){
			String caseID = iterator.next();
			Map<String,Double> tfidf = new HashMap<String,Double>();
			Map<String,Double> tf = tfs.get(caseID);
			double sum2 = 0.0;
			for(Map.Entry<String, Double> entry: tf.entrySet()){
				String key = entry.getKey();
				double value = entry.getValue();
				double numerator = value*idfs.get(key);
				sum2 += numerator*numerator;
			}
			sum2 = Math.sqrt(sum2);
			for(Map.Entry<String, Double> entry: tf.entrySet()){
				String key = entry.getKey();
				double numerator = entry.getValue();
				double tfidfValue = numerator/sum2;
				tfidf.put(key, tfidfValue);
			}
			res.put(caseID, tfidf);
		}
		return res;
	}
	public static List<WenshuWords> load(){
		List<WenshuWords> res = new ArrayList<WenshuWords>();
		File dir = new File("F:\\tmp\\wenshudata\\");
		File[] file = dir.listFiles();
		int length = file.length;
		for(int i = 0;i < length;i++){
			try{
				InputStream is = new FileInputStream(file[i]);
				InputStreamReader reader = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(reader);
				String caseID = br.readLine();
				if(caseID == null)
					continue;
				System.out.println(caseID);
				String caseBrief = br.readLine();
				String line = br.readLine();
				String[] words = line.split(" ");
				List<String> list = new ArrayList<String>();
				for(String word:words){
					list.add(word);
				}
				br.close();
				reader.close();
				is.close();
				WenshuWords wenshuWords = new WenshuWords(caseID,caseBrief,list);
				res.add(wenshuWords);
			} catch (IOException e) {
				continue;
			}
		}
		return res;
	}
	private static Map<String,Double> idf = null;
	public static Map<String,Double> loadIDF(){
		if(idf != null)
			return idf;
		Map<String,Double> res = new HashMap<String,Double>();
		MySQLConnection connection = new MySQLConnectionImpl("wenshu");
		connection.connect();
		String sql = "select word,idf from idf;";
		List<String> list = connection.query(sql);
		for(String line: list){
			String[] s = line.split(" ");
			res.put(s[0], Double.parseDouble(s[1]));
		}
		connection.release();
		idf = res;
		return res;
	}
	public static Map<String,Map<String,Double>> loadTF(){
		Map<String,Map<String,Double>> res = new HashMap<String,Map<String,Double>>();
		File dir = new File("F:\\tmp\\tfidf\\tf");
		File[] files = dir.listFiles();
		for(File file: files){
			try{
				InputStream is = new FileInputStream(file);
				InputStreamReader reader = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(reader);
				String caseID = br.readLine();
				Map<String,Double> map = new HashMap<String,Double>();
				String line = null;
				while((line = br.readLine()) != null){
					String[] e = line.split("=");
					map.put(e[0], Double.parseDouble(e[e.length-1]));
				}
				res.put(caseID, map);
				br.close();
				reader.close();
				is.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
	public static Map<String,Map<String,Double>> loadTFIDF(){
		Map<String,Map<String,Double>> res = new HashMap<String,Map<String,Double>>();
		File dir = new File("F:\\tmp\\tfidf\\tfidf");
		File[] files = dir.listFiles();
		for(File file: files){
			try{
				InputStream is = new FileInputStream(file);
				InputStreamReader reader = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(reader);
				String caseID = br.readLine();
				System.out.println("tfidf:"+caseID);
				Map<String,Double> map = new HashMap<String,Double>();
				String line = null;
				while((line = br.readLine()) != null){
					String[] e = line.split("=");
					map.put(e[0], Double.parseDouble(e[1]));
				}
				res.put(caseID, map);
				br.close();
				reader.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return res;
	}
	public static void tag(){
		WordSegmenter ws = new WordSegmenter();
		Map<String,Map<String,Double>> tfidfs = loadTFIDF();
		Iterator<String> iterator = tfidfs.keySet().iterator();
		while(iterator.hasNext()){
			String caseID = iterator.next();
			Map<String,Double> map = tfidfs.get(caseID);
			File file = new File("F:\\tmp\\newtfidf\\newtfidf\\"+caseID+".txt");
			System.out.println(caseID);
			try{
				OutputStream os = new FileOutputStream(file);
				OutputStreamWriter ow = new OutputStreamWriter(os);
				BufferedWriter bw = new BufferedWriter(ow);
				bw.write(caseID);
				bw.newLine();
				List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String,Double>>(map.entrySet());
				Collections.sort(list, new Comparator<Map.Entry<String, Double>>(){

					@Override
					public int compare(Entry<String, Double> arg0, Entry<String, Double> arg1) {
						double tfidf0 = arg0.getValue();
						double tfidf1 = arg1.getValue();
						if(tfidf0 < tfidf1){
							return 1;
						}
						else if(tfidf0 > tfidf1){
							return -1;
						}
						else{
							return 0;
						}
					}
					
				});
				for(Map.Entry<String, Double> entry: list){
					if(!ws.tag(entry.getKey()).equals("NR")){
						bw.write(entry.getKey()+"="+entry.getValue());
						bw.newLine();
					}
				}
				bw.close();
				ow.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args){
//		List<WenshuWords> wenshus = load();
		TF_IDF tfidf = new TF_IDF();
//		tfidf.loadTFIDF();
//		Map<String,Double> idfMap = tfidf.calculateIDF(wenshus);
//		List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String,Double>>(idfMap.entrySet());
//		Collections.sort(list,new Comparator<Map.Entry<String,Double>>(){
//
//			@Override
//			public int compare(Entry<String, Double> arg0, Entry<String, Double> arg1) {
//				double idf0 = arg0.getValue();
//				double idf1 = arg1.getValue();
//				if(idf0 > idf1){
//					return 1;
//				}
//				else if(idf0 < idf1){
//					return -1;
//				}
//				else{
//					return 0;
//				}
//			}
//			
//		});
//		File file = new File("F:\\tmp\\tfidf\\idf.txt");
//		try{
//			OutputStream os = new FileOutputStream(file);
//			OutputStreamWriter ow = new OutputStreamWriter(os);
//			BufferedWriter bw = new BufferedWriter(ow);
//			for(Map.Entry<String, Double> entry: list){
//				bw.write(entry.getKey()+"="+entry.getValue());
//				bw.newLine();
//			}
//			bw.close();
//			ow.close();
//			os.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		for(WenshuWords wenshu: wenshus){
//			Map<String, Double> map = tfidf.calculateTF(wenshu);
//			System.out.println(wenshu.getCaseID());
//			File file = new File("F:\\tmp\\tfidf\\tf\\"+wenshu.getCaseID()+".txt");
//			try{
//				OutputStream os = new FileOutputStream(file);
//				OutputStreamWriter ow = new OutputStreamWriter(os);
//				BufferedWriter bw = new BufferedWriter(ow);
//				bw.write(wenshu.getCaseID());
//				bw.newLine();
//				List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String,Double>>(map.entrySet());
//				Collections.sort(list,new Comparator<Map.Entry<String, Double>>(){
//
//					@Override
//					public int compare(Entry<String, Double> arg0, Entry<String, Double> arg1) {
//						double tf0 = arg0.getValue();
//						double tf1 = arg1.getValue();
//						if(tf0 < tf1){
//							return 1;
//						}
//						else if(tf0 > tf1){
//							return -1;
//						}
//						else{
//							return 0;
//						}
//					}
//					
//				});
//				for(Map.Entry<String, Double> entry: list){
//					bw.write(entry.getKey()+"="+entry.getValue());
//					bw.newLine();
//				}
//				bw.close();
//				ow.close();
//				os.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		Map<String,Double> idfs = loadIDF();
		Map<String,Map<String,Double>> tfs = loadTF();
		Map<String,Map<String,Double>> tfidfs = tfidf.calculateTFIDF(idfs, tfs);
		Iterator<String> iterator = tfidfs.keySet().iterator();
		while(iterator.hasNext()){
			String caseID = iterator.next();
			Map<String,Double> map = tfidfs.get(caseID);
			File file = new File("F:\\tmp\\tfidf\\tfidf-res\\"+caseID+".txt");
			System.out.println(caseID);
			try{
				OutputStream os = new FileOutputStream(file);
				OutputStreamWriter ow = new OutputStreamWriter(os);
				BufferedWriter bw = new BufferedWriter(ow);
				bw.write(caseID);
				bw.newLine();
				List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String,Double>>(map.entrySet());
				Collections.sort(list, new Comparator<Map.Entry<String, Double>>(){

					@Override
					public int compare(Entry<String, Double> arg0, Entry<String, Double> arg1) {
						double tfidf0 = arg0.getValue();
						double tfidf1 = arg1.getValue();
						if(tfidf0 < tfidf1){
							return 1;
						}
						else if(tfidf0 > tfidf1){
							return -1;
						}
						else{
							return 0;
						}
					}
					
				});
				for(Map.Entry<String, Double> entry: list){
					bw.write(entry.getKey()+"="+entry.getValue());
					bw.newLine();
				}
				bw.close();
				ow.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
