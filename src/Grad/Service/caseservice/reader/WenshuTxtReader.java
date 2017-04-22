package Grad.Service.caseservice.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WenshuTxtReader implements IWenshuReader{
	@Override
	public List<String> read(String path) {
		List<String> result = new ArrayList<String>();
		File file = new File(path);
		try{
			FileInputStream fin = new FileInputStream(file);
			InputStreamReader reader = new InputStreamReader(fin);
			BufferedReader br = new BufferedReader(reader);
			String line = null;
			while((line = br.readLine()) != null){
				result.add(line);
			}
			br.close();
			reader.close();
			fin.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
