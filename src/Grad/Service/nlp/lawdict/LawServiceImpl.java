package Grad.Service.nlp.lawdict;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LawServiceImpl implements LawService{
		
	@Override
	public String getLawContent(String lawname, String lawitem) {
		String filePath = "law/"+lawname+".txt";
		File file = new File(filePath);
		try {
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();
				if(line.startsWith(lawitem)){
					return line.split(" ")[1];
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	//Test
	public static void main(String[] args){
		LawService lawService = new LawServiceImpl();
		System.out.println(lawService.getLawContent("中华人民共和国刑法","第一百一十一条" ));
	}

}
