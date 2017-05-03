package tfidf;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
public class DistanceCalc {
	public double calculateCosDistance(WenshuVector vec1,WenshuVector vec2){
		Map<String,Double> map1 = vec1.getVector();
		Map<String,Double> map2 = vec2.getVector(); 
		return this.calculateCosDistance(map1, map2);
	}
	public double calculateCosDistance(Map<String,Double> map1,Map<String,Double> map2){
		Set<String> set = new HashSet<String>();
		set.addAll(map1.keySet());
		set.addAll(map2.keySet());
		double sum = 0;
		Iterator<String> iterator = set.iterator();
		while(iterator.hasNext()){
			String word = iterator.next();
			double tfidf1 = map1.containsKey(word)?map1.get(word):0.0;
			double tfidf2 = map2.containsKey(word)?map2.get(word):0.0;
			double sub = Math.abs(tfidf1-tfidf2);
			sum += sub*sub;
		}
		double result = Math.sqrt(sum);
		return result;
	}
	public static void main(String[] args){
		DistanceCalc distance = new DistanceCalc();
		WenshuVectorGenerator generator = new WenshuVectorGenerator();
		Map<String,String> typeMap = generator.loadType();
		Map<String,Map<String,Double>> tfidfMap = TF_IDF.loadTFIDF();
		List<WenshuVector> vectors = generator.generateVector(typeMap, tfidfMap);
		int size = vectors.size();
		for(int i = 0;i < size;i++){
			WenshuVector vector = vectors.get(i);
			Map<String,Double> map = new HashMap<String,Double>();
			for(int j = 0;j < size;j++){
				if(vector.getCaseBrief().equals(vectors.get(j).getCaseBrief())&&i!=j){
					double dist = distance.calculateCosDistance(vector, vectors.get(j));
					map.put(vectors.get(j).getCaseID(), dist);
				}
			}
			List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String,Double>>(map.entrySet());
			Collections.sort(list,new Comparator<Map.Entry<String,Double>>(){

				@Override
				public int compare(Entry<String, Double> arg0, Entry<String, Double> arg1) {
					double d0 = arg0.getValue();
					double d1 = arg1.getValue();
					if(d0 < d1){
						return 1;
					}
					else if(d0 > d1){
						return -1;
					}
					else{
						return 0;
					}
				}
				
			});
			File file = new File("F:\\tmp\\tfidf\\sim\\"+vector.getCaseID()+".txt");
			try {
				OutputStream os = new FileOutputStream(file);
				OutputStreamWriter ow = new OutputStreamWriter(os);
				BufferedWriter bw = new BufferedWriter(ow);
				for(int j = 0;j < 5;j ++){
					String key = null;
					try{
						key = list.get(j).getKey();
					} catch(Exception e) {
						break;
					}
					bw.write(key);
					bw.newLine();
				}
				bw.close();
				ow.close();
				os.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
