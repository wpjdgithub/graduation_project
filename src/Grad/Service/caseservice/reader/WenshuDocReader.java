package Grad.Service.caseservice.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hwpf.extractor.WordExtractor;

public class WenshuDocReader implements IWenshuReader{

	@Override
	public List<String> read(String path) {
		List<String> result = new ArrayList<String>();
		try {
            InputStream is = new FileInputStream(new File(path));
            WordExtractor ex = new WordExtractor(is);
            String text = ex.getText();
            String[] e = text.split("\n");
            for(int i = 0;i < e.length;i++){
            	result.add(e[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return result;
	}

}
