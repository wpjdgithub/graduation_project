package tfidf;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
public class WenshuVectorGenerator {
	public Set<String> set;
	public WenshuVectorGenerator(){
		this.set = new HashSet<String>();
		try {
			Scanner scanner = new Scanner(new File("wenshudata\\wordcount"));
			while(scanner.hasNextLine()){
				set.add(scanner.nextLine());
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Map<String,Map<String,Double>> generateVector(Map<String,Map<String,Double>> vectorMap){
		Map<String,Map<String,Double>> map = 
				new HashMap<String,Map<String,Double>>();
		List<Map.Entry<String, Map<String,Double>>> list = 
				new ArrayList<Map.Entry<String,Map<String,Double>>>(vectorMap.entrySet());
		for(Map.Entry<String, Map<String, Double>> entry:list){
			Map<String,Double> vec = new HashMap<String,Double>();
			Iterator<String> iter = set.iterator();
			while(iter.hasNext()){
				String key = iter.next();
				if(entry.getValue().containsKey(key)){
					vec.put(key, entry.getValue().get(key));
				}
				else{
					vec.put(key, 0.0);
				}
			}
			map.put(entry.getKey(), vec);
			
		}
		return map;
	}
	public static void main(String[] args) throws IOException{
		String path = "F:\\tmp\\wenshuvec\\";
		List<Map.Entry<String, Map<String,Double>>> list = 
				new ArrayList<Map.Entry<String,Map<String,Double>>>(TF_IDF.loadTFIDF().entrySet());
		WenshuVectorGenerator gen = new WenshuVectorGenerator();
		for(Map.Entry<String, Map<String, Double>> entry:list){
			Map<String,Double> vec = new HashMap<String,Double>();
			Iterator<String> iter = gen.set.iterator();
			while(iter.hasNext()){
				String key = iter.next();
				if(entry.getValue().containsKey(key)){
					vec.put(key, entry.getValue().get(key));
				}
				else{
					vec.put(key, 0.0);
				}
			}
			String caseId = entry.getKey();
			File file = new File(path+caseId+".txt");
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			iter = vec.keySet().iterator();
			while(iter.hasNext()){
				String key = iter.next();
				double value = vec.get(key);
				System.out.println(caseId+" "+key+" "+value);
				bw.write(key+"="+value);
				bw.newLine();
			}
			bw.close();
			fw.close();
		}
	}
}