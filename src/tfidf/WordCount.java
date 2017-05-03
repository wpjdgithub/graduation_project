package tfidf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class WordCount {
	public WordCount(){
		
	}
	public Map<String,Integer> wordcount(List<String> words){
		Map<String,Integer> res = new HashMap<String,Integer>();
		for(String word: words){
			if(res.containsKey(word)){
				int count = res.get(word)+1;
				res.put(word, count);
			}
			else{
				res.put(word, 1);
			}
		}
		return res;
	}
	public static void main(String[] args) throws FileNotFoundException{
		File file = new File("F:\\tmp\\tfidf\\vector\\（2001）南民初字第148号.txt");
		Scanner scanner = new Scanner(file);
		int count = -2;
		while(scanner.hasNext()){
			scanner.next();
			count++;
		}
		scanner.close();
		System.out.println(count);
	}
}
