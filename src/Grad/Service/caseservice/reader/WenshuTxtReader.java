package Grad.Service.caseservice.reader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
public class WenshuTxtReader implements IWenshuReader{
	@Override
	public String read(String path) {
		StringBuilder sb = new StringBuilder();
		File file = new File(path);
		try{
			FileInputStream fin = new FileInputStream(file);
			InputStreamReader reader = new InputStreamReader(fin);
			BufferedReader br = new BufferedReader(reader);
			String line = null;
			while((line = br.readLine()) != null){
				sb.append(line).append("\n");
			}
			br.close();
			reader.close();
			fin.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		String result = sb.toString();
		return result;
	}
}