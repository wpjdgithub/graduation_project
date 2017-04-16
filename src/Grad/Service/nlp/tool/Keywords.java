package Grad.Service.nlp.tool;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
public class Keywords {
	private static Set<String> keywords = new HashSet<String>();
	static{
		File file = new File("keywords.txt");
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				keywords.add(scanner.nextLine());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static Iterator<String> getKeywordsIterator(){
		return Keywords.keywords.iterator();
	}
}
