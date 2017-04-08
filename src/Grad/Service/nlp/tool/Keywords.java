package Grad.Service.nlp.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Keywords {
	private static Set<String> keywords = new HashSet<String>();
	static{
		File file = new File("F:/GitRepo/WenshuProcessing/WenshuNLP/src/service/nlp/keywords.txt");
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
//	public static void main(String[] args) throws IOException{
//		File dir = new File("F:/GitRepo/WenshuCrawler/WenshuCrawler/wenshu4/");
//		File[] file = dir.listFiles();
//		HashSet<String> keywordsSet = new HashSet<String>();
//		for(int i = 0;i < file.length;i++){
//			Scanner scanner = new Scanner(file[i]);
//			for(int j = 0;j < 7;j++)
//				scanner.nextLine();
//			if(scanner.hasNextLine()){
//				String keyline = scanner.nextLine();
//				if(keyline.startsWith("%"))
//					continue;
//				String[] keyword = keyline.split(" ");
//				for(int k = 1;k < keyword.length;k += 2){
//					keywordsSet.add(keyword[k]);
//				}
//			}
//			scanner.close();
//			System.out.println(keywordsSet.size());
//		}
//		Iterator<String> iterator = keywordsSet.iterator();
//		FileWriter fw = 
//				new FileWriter(new File("F:/GitRepo/WenshuProcessing/WenshuNLP/src/service/nlp/keywords.txt"));
//		BufferedWriter bw = new BufferedWriter(fw);
//		while(iterator.hasNext()){
//			String keyword = iterator.next();
//			bw.write(keyword);
//			bw.newLine();
//		}
//		bw.close();
//		fw.close();
//	}
}
