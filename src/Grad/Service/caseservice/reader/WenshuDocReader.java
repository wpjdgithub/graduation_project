package Grad.Service.caseservice.reader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.poi.hwpf.extractor.WordExtractor;
public class WenshuDocReader implements IWenshuReader{
	@Override
	public String read(String path) {
		StringBuilder sb = new StringBuilder();
		try {
            InputStream is = new FileInputStream(new File(path));
            WordExtractor ex = new WordExtractor(is);
            String text = ex.getText();
            String[] e = text.split("\n");
            for(int i = 0;i < e.length;i++){
            	sb.append(e[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		String result = sb.toString();
		return result;
	}
}