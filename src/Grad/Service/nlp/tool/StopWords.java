package Grad.Service.nlp.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class StopWords {
	public static Set<String> set = new HashSet<String>();
	static{
		File file = new File("wenshudata/stopwords.txt");
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				line = line.trim();
				set.add(line);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
	public static boolean isStopWords(String word){
		if(set.contains(word)){
			return true;
		}
		else{
			return false;
		}
	}
}
