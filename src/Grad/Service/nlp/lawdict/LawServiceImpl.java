package Grad.Service.nlp.lawdict;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
				String[] s = line.split(" ");
				if(s[0].contains(lawitem)){
					return s[1];
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Set<String> getAllLawNames() {
		Set<String> set = new HashSet<String>();
		File dir = new File(this.path+"law\\");
		String[] lawname = dir.list();
		for(int i = 0;i < lawname.length;i++){
			if(lawname[i].endsWith(".txt")){
				set.add(lawname[i]);
			}
		}
		return set;
	}
}
