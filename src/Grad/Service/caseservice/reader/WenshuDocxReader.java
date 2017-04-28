package Grad.Service.caseservice.reader;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
public class WenshuDocxReader implements IWenshuReader{
	@Override
	public String read(String path) {
		StringBuilder sb = new StringBuilder();
		try {
            OPCPackage opcPackage = POIXMLDocument.openPackage(path);
            POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
            String text = extractor.getText();
            String[] line = text.split("\n");
            for(int i = 0;i < line.length;i++){
            	sb.append(line[i]).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		String result = sb.toString();
		return result;
	}
}