package Grad.Service.nlp.tool;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
public class Keywords {
	private String path;
	private Set<String> keywords = new HashSet<String>();
	public Keywords(String path){
		this.path = path;
		File file = new File(this.path+"keywords.txt");
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
	public Iterator<String> getKeywordsIterator(){
		return keywords.iterator();
	}
}
