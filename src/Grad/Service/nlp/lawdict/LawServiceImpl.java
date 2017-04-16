package Grad.Service.nlp.lawdict;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LawServiceImpl implements LawService{
	
	private String path;
	
	public LawServiceImpl(String path){
		this.path = path;
	}
	
	public void setPath(String path){
		this.path = path;
	}
		
	@Override
	public String getLawContent(String lawname, String lawitem) {
		String filePath = this.path+"law\\"+lawname+".txt";
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
}
