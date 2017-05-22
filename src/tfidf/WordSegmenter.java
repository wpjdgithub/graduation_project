package tfidf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import Grad.Service.dataservice.WenshuDataService;
import Grad.Service.dataservice.impl.WenshuDataServiceImpl;
import Grad.Service.nlp.tool.StopWords;
import Grad.Service.wenshu.Wenshu;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class WordSegmenter {
	private String baseDir;
	private CRFClassifier<CoreLabel> segmenter;
	private MaxentTagger tagger;
	public WordSegmenter(){
		this.baseDir = System.getProperty("graduation_project", "edu/stanford/nlp/models/segmenter/chinese");
		try {
			System.setOut(new PrintStream(System.out, true, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    Properties props = new Properties();
	    props.setProperty("sighanCorporaDict", baseDir);
	    props.setProperty("serDictionary", baseDir + "/dict-chris6.ser.gz");
	    props.setProperty("inputEncoding", "UTF-8");
	    props.setProperty("sighanPostProcessing", "true");
	    this.segmenter = new CRFClassifier<CoreLabel>(props);
	    this.segmenter.loadClassifierNoExceptions(baseDir + "/ctb.gz", props);
	    this.tagger = new MaxentTagger("edu/stanford/nlp/models/pos-tagger/chinese-distsim/chinese-distsim.tagger");
	}
	public List<String> chineseWordSegmenter(String sentence){
		List<String> res = new ArrayList<String>();
		List<String> list = this.segmenter.segmentString(sentence);
		for(int i = 0;i < list.size();i++){
			String word = list.get(i);
			if(word.contains("《")){
				String law = "";
				int index = i+1;
				boolean flag = true;
				word = list.get(++i);
				do{
					law += word;
					try{
						word = list.get(++i);
					} catch(ArrayIndexOutOfBoundsException e){
						i = index;
						flag = false;
						break;
					}
				} while(!word.contains("》"));
				if(flag){
					res.add(law);
					word = list.get(++i);
				}
			}
			if(!this.canBeAdded(word) && !this.containDigit(word) 
					&& word.length() != 1 && !StopWords.isStopWords(word)
					&& !this.containAlphabet(word))
				res.add(word);
		}
		return res;
	}
	private boolean canBeAdded(String s){
		if(s.length() == 1){
			char c = s.charAt(0);
			if(Character.isWhitespace(c)){
				return true;
			}
			else{
				if(c == '，' || c == '。' || c == '：' || c == '；' || c == '“' || c == '”'
						|| c == '、' || c == '(' || c == ')' || c == '（' || c == '）' 
						|| c == '.' || c == ','){
					return true;
				}
				else{
					return false;
				}
			}
		}
		else if(s.length() == 0){
			return true;
		}
		else{
			return false;
		}
	}
	private boolean containDigit(String s){
		int length = s.length();
		if(length == 0){
			return false;
		}
		else{
			for(int i = 0; i < length;i++){
				char c = s.charAt(i);
				if(Character.isDigit(c) || c == '○' || c == '一' || c == '二' || c == '三' 
						|| c == '四' || c == '五' || c == '六' || c == '七' || c == '八'
						|| c == '九' || c == '十')
					return true;
			}
			return false;
		}
	}
	private boolean containAlphabet(String word){
		int length = word.length();
		if(length == 0){
			return false;
		}
		else{
			for(int i = 0;i < length;i++){
				char c = word.charAt(i);
				if((c <= 'z' && c >= 'a')||(c <= 'Z' && c >= 'A'))
					return true;
			}
			return false;
		}
	}
	public String tag(String word){
		return this.tagger.tagString(word).split("#")[1].trim();
	}
	public static void main(String[] args){
//		WenshuDataService wenshuDataService = new WenshuDataServiceImpl();
//		Wenshu wenshu = wenshuDataService.getWenshuByCaseID("（2012）南民初字第1077号");
//		String text = wenshu.getFullText();
		WordSegmenter ws = new WordSegmenter();
//		List<String> words = ws.chineseWordSegmenter(text);
//		System.out.println(words.toString());
//		List<Wenshu> wenshus = wenshuDataService.getAllWenshuData();
//		for(Wenshu wenshu:wenshus){
//			String filepath = "F:\\tmp\\wenshudata\\"+wenshu.getCaseID()+".txt";
//			System.out.println(filepath);
//			File file = new File(filepath);
//			try{
//				OutputStream os = new FileOutputStream(file);
//				OutputStreamWriter ow = new OutputStreamWriter(os);
//				BufferedWriter bw = new BufferedWriter(ow);
//				bw.write(wenshu.getCaseID());
//				bw.newLine();
//				bw.write(wenshu.getCaseBrief());
//				bw.newLine();
//				List<String> words = ws.chineseWordSegmenter(wenshu.getFullText());
//				StringBuilder sb = new StringBuilder();
//				for(String word:words){
//					sb.append(word).append(" ");
//				}
//				String line = sb.toString().trim();
//				bw.write(line);
//				bw.newLine();
//				bw.close();
//				ow.close();
//				os.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		System.out.println(ws.tag("潘照利").equals("NR "));
		System.out.println(ws.tag("天津市"));
		System.out.println(ws.tag("津南区"));
		System.out.println(ws.tag("驾驶"));
		System.out.println(ws.tag("检察院"));
		System.out.println(ws.tag("判决"));
	}
}
