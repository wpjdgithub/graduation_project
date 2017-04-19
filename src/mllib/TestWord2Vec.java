package mllib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.CharSet;

import mllib.word2vec.Learn;
import mllib.word2vec.Word2VEC;

public class TestWord2Vec {
	public static void main(String[] args) throws IOException{
//		Learn learn = new Learn();
//		learn.learnFile(new File("F:\\tmp\\model\\words.txt"));
//		learn.saveModel(new File("F:\\tmp\\model\\model"));
		Word2VEC vec = new Word2VEC();
		vec.loadJavaModel("F:\\tmp\\model\\model");
		List<String> wordList = new ArrayList<String>();
		File file = new File("F:\\Programming.Project\\GitRepo\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\graduation_project\\keyword.txt");
//		Scanner scanner = new Scanner(file);
//		while(scanner.hasNextLine()){
//			wordList.add(scanner.nextLine());
//		}
//		scanner.close();
		FileInputStream fin = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(fin);
		BufferedReader br = new BufferedReader(reader);
		String line = null;
		while((line = br.readLine()) != null){
			wordList.add(line);
			System.out.println(line);
		}
		br.close();
		reader.close();
		fin.close();
		for (String word : wordList) {
		    System.out.println(word + " " +vec.distance(word));
		}
	}
}
