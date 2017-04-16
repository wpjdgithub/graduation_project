package mllib;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MachineLearningServiceImpl implements MachineLearningService{
	
	private String highFrequencyFilePath = "F:\\Programming.Project\\data\\highfrequency.txt";
	private Set<String> highFrequencySet = new HashSet<String>();
	
	public MachineLearningServiceImpl(String filePath){
		this.highFrequencyFilePath = filePath;
		init();
	}
	
	public MachineLearningServiceImpl(){
		init();
	}
	
	private void init(){
		File file = new File(this.highFrequencyFilePath);
		try{
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				String word = scanner.nextLine();
				this.highFrequencySet.add(word);
			}
			scanner.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public String getHighFrequencyFilePath() {
		return highFrequencyFilePath;
	}

	public void setHighFrequencyFilePath(String highFrequencyFilePath) {
		this.highFrequencyFilePath = highFrequencyFilePath;
	}

	@Override
	public Map<String, Set<String>> cluster(Map<String, Map<String, Double>> wenshus) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private double calculateCosineDistance(Map<String,Double> vector1,Map<String,Double> vector2){
		double xy = 0;
		double xx = 0;
		double yy = 0;
		Iterator<String> iterator = vector1.keySet().iterator();
		while(iterator.hasNext()){
			String key1 = iterator.next();
			if(this.highFrequencySet.contains(key1)){
				continue;
			}
			
		}
		xx = Math.sqrt(xx);
		yy = Math.sqrt(yy);
		if(xx == 0.0 || yy == 0.0)
			return 0.0;
		else{
			double result = xy/(xx*yy);
			return result;
		}
	}

}
