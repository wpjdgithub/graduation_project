package Grad.Service.caseservice.reader;

public class WenshuReaderTool {
	private static IWenshuReader txtReader = new WenshuTxtReader();
	private static IWenshuReader docReader = new WenshuDocReader();
	private static IWenshuReader docxReader = new WenshuDocxReader();
	public static IWenshuReader getWenshuReader(WenshuType type){
		IWenshuReader reader;
		switch(type){
		case doc:
			reader = docReader;
			break;
		case docx:
			reader = docxReader;
			break;
		case txt:
			reader = txtReader;
			break;
		default:
			reader = null;
		}
		return reader;
	}
}
