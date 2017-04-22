package Grad.Service.caseservice.reader;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
public class WenshuDocxReader implements IWenshuReader{
	@Override
	public List<String> read(String path) {
		List<String> result = new ArrayList<String>();
		try {
            OPCPackage opcPackage = POIXMLDocument.openPackage(path);
            POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
            String text = extractor.getText();
            String[] line = text.split("\n");
            for(int i = 0;i < line.length;i++){
            	result.add(line[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return result;
	}
}