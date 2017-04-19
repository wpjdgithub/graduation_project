package mllib;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import Grad.Service.dataservice.WenshuDataService;
import Grad.Service.dataservice.impl.WenshuDataServiceImpl;
import Grad.Service.wenshu.Wenshu;
public class Wenshu2Txt {
	private WenshuDataService wenshuDataService;
	public Wenshu2Txt(){
		this.wenshuDataService = new WenshuDataServiceImpl();
	}
	public void transform(String path){
		List<Wenshu> wenshus = this.wenshuDataService.getAllWenshuData();
		int size = wenshus.size();
		for(int i = 0;i < size;i++){
			Wenshu wenshu = wenshus.get(i);
			String caseID = wenshu.getCaseID().trim();
			String fullText = wenshu.getFullText();
			String[] lines = fullText.split("\n");
			File txtFile = new File(path+wenshu.getFilename()+".txt");
			try{
				FileOutputStream fout = new FileOutputStream(txtFile);
				OutputStreamWriter ow = new OutputStreamWriter(fout);
				BufferedWriter bw = new BufferedWriter(ow);
				for(int j = 0;j < lines.length;j++){
					String line = lines[j].trim();
					bw.write(line);
					bw.newLine();
				}
				bw.close();
				ow.close();
				fout.close();
			} catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args){
		Wenshu2Txt wenshu2txt = new Wenshu2Txt();
		wenshu2txt.transform("F:\\tmp\\");
	}
}
