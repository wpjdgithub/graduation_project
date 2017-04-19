package mllib;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import Grad.Service.nlp.NLPService;
import Grad.Service.nlp.impl.NLPServiceImpl;
import Grad.Service.nlp.tool.StopWords;
public class WenshuSegementor {
	private NLPService nlpService;
	public WenshuSegementor(){
		this.nlpService = new NLPServiceImpl();
	}
	public List<String> doWordSegmentation(File file) throws IOException{
		List<String> result = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		FileInputStream fin = new FileInputStream(file);
		InputStreamReader freader = new InputStreamReader(fin);
		BufferedReader br = new BufferedReader(freader);
		String line = null;
		while((line = br.readLine()) != null){
			sb.append(line);
		}
		br.close();
		freader.close();
		fin.close();
		String fullText = sb.toString();
		String[] sentences = fullText.split("[;；,，.。？?‘’“”\"、:：/【】%+-÷〇…()&○（）—#《》]");
		for(int i = 0;i < sentences.length;i++){
			String sentence = sentences[i];
			List<String> list = this.nlpService.chineseWordSegmentation(sentence);
			for(String word:list){
				if(this.isValid(word)){
					result.add(word);
				}
			}
		}
		return result;
	}
	private boolean isEnglishCharacter(char c){
		if((c <= 'z' && c >= 'a') ||(c <= 'Z' && c >= 'A')){
			return true;
		}
		else{
			return false;
		}
	}
	private boolean isInteger(char c){
		if(c <= '9' && c >= '0'){
			return true;
		}
		else{
			return false;
		}
	}
	private boolean isValid(String word){
		int length = word.length();
		for(int i = 0;i < length;i++){
			char c = word.charAt(i);
			if(this.isEnglishCharacter(c) || this.isInteger(c)){
				return false;
			}
		}
		return true;
	}
	public static void main(String[] args) throws IOException{
		WenshuSegementor ws = new WenshuSegementor();
		File dir = new File("F:\\tmp\\");
		File[] files = dir.listFiles();
		File newFile = new File("F:\\tmp\\model\\words.txt");
		FileOutputStream fout = new FileOutputStream(newFile);
		OutputStreamWriter ow = new OutputStreamWriter(fout);
		BufferedWriter bw = new BufferedWriter(ow);
		for(int i = 0;i < files.length;i++){
			if(!files[i].isDirectory()){
				List<String> words = ws.doWordSegmentation(files[i]);
				int size = words.size();
				for(int j = 0;j < size;j++){
					String word = words.get(j);
					if(StopWords.isStopWords(word))
						continue;
					bw.write(word);
					if(j != size-1){
						bw.write(" ");
					}
					else{
						bw.write("\n");
					}
				}
				System.out.println(files[i].getAbsolutePath());
			}
		}
		bw.close();
		ow.close();
		fout.close();
	}
}
