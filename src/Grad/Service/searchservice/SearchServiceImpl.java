package Grad.Service.searchservice;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.lucene.queryparser.classic.ParseException;
import Grad.Bean.CaseBrief;
import Grad.Bean.CaseFilter;
import Grad.Bean.CaseSearchRes;
import Grad.Bean.SearchInfo;
import Grad.Service.SearchService;
import Grad.Service.dataservice.jdbc.MySQLConnection;
import Grad.Service.dataservice.jdbc.MySQLConnectionImpl;
import Grad.Service.nlp.tool.Keywords;
import Grad.Service.searchservice.lucene.SearchFiles;
import Grad.Service.searchservice.lucene.SearchItem;
import Grad.Service.wenshu.Wenshu;
public class SearchServiceImpl implements SearchService{
	private SearchFiles searchTool;
	private Keywords keywords;
	public SearchServiceImpl(){
		this.searchTool = new SearchFiles();
		this.keywords = new Keywords("/");
	}
	public SearchServiceImpl(String path){
		this.searchTool = new SearchFiles(path);
		this.keywords = new Keywords(path);
	}
	@Override
	public CaseSearchRes search(String input) {
		String[] keyword = input.split(" ");
		int length = keyword.length;
		List<SearchItem> items = new ArrayList<SearchItem>();
		List<String> values = new ArrayList<String>();
		for(int i = 0;i < length;i++){
			items.add(SearchItem.fulltext);
			values.add(keyword[i]);
		}
		List<Wenshu> list;
		try {
			list = this.searchTool.search(items, values);
		} catch (IOException | ParseException e) {
			list = new ArrayList<Wenshu>();
		}
		CaseSearchRes result = this.change(list);
		return result;
	}
	@Override
	public CaseSearchRes search(SearchInfo info) {
		List<SearchItem> items = new ArrayList<SearchItem>();
		List<String> values = new ArrayList<String>();
		String fullText = info.getFull_text();
		String caseBrief = info.getBrief();
//		String caseName = info.getName_case();
		String caseID = info.getNum();
		String courtLevel = info.getCourtLevel();
		String caseType = info.getCaseType();
		String caseProgram = info.getCaseProgram();
		String documentType = info.getDocumentType();
		String judger = info.getJudex();
		String litigant = info.getLitigant();
		String laws = info.getLegistative_authority();
		if(!caseID.equals("")){
			items.add(SearchItem.caseid);
			values.add(caseID);
		}
		if(!courtLevel.equals("")){
			items.add(SearchItem.courtlevel);
			values.add(courtLevel);
		}
		if(!fullText.equals("")){
			items.add(SearchItem.fulltext);
			values.add(fullText);
		}
		if(!caseBrief.equals("")){
			items.add(SearchItem.casebrief);
			values.add(caseBrief);
		}
		if(!caseType.equals("")){
			items.add(SearchItem.casetype);
			values.add(caseType);
		}
		if(!caseProgram.equals("")){
			items.add(SearchItem.caseprogram);
			values.add(caseProgram);
		}
		if(!documentType.equals("")){
			items.add(SearchItem.documenttype);
			values.add(documentType);
		}
		if(!judger.equals("")){
			items.add(SearchItem.judgers);
			values.add(judger);
		}
		if(!litigant.equals("")){
			items.add(SearchItem.participants);
			values.add(litigant);
		}
		if(!laws.equals("")){
			items.add(SearchItem.laws);
			values.add(laws);
		}
		List<Wenshu> wenshus;
		try {
			wenshus = this.searchTool.search(items, values);
		} catch (IOException | ParseException e) {
			wenshus = new ArrayList<Wenshu>();
		}
		CaseSearchRes result = this.change(wenshus);
		return result;
	}
	private CaseSearchRes change(List<Wenshu> list){
		CaseSearchRes result = new CaseSearchRes();
		Map<String,Integer> keywordMap = new HashMap<String,Integer>();//按关键词筛选
		Map<String,Integer> briefMap = new HashMap<String,Integer>();//按案由筛选
		Map<String,Integer> courtLevelMap = new HashMap<String,Integer>();//按法院层级筛选
		Map<String,Integer> yearMap = new HashMap<String,Integer>();//按年份筛选
		Map<String,Integer> documentTypeMap = new HashMap<String,Integer>();//按文书类型筛选
		MySQLConnection connection = new MySQLConnectionImpl("wenshu");
		connection.connect();
		int size = list.size();
		for(int i = 0;i < size;i++){
			Wenshu wenshu = list.get(i);
			CaseBrief casebrief = new CaseBrief();
			casebrief.setTitle(wenshu.getCaseName());
			casebrief.setId(wenshu.getCaseID());
			casebrief.setDate(wenshu.getCaseYear());
			casebrief.setBrief(wenshu.getCaseBrief());
			casebrief.setProcess_judgement(wenshu.getCaseProgram());
			casebrief.setType_text(wenshu.getDocumentName());
			casebrief.setCourt(wenshu.getCaseName().split(" ")[0]);
			casebrief.setCourtLevel(wenshu.getCourtLevel());
			casebrief.setSource("天津最高人民法院");
			casebrief.setYear(wenshu.getCaseYear());
			result.addCaseBrief(casebrief);
			String keywords = connection.query("select keyword from keyword where caseid='"+wenshu.getCaseID()+"';").get(0);
			StringBuilder sb = new StringBuilder();
			String[] w = keywords.split(" ");
			if(w.length > 6){
				for(int j = 0;j < 6;j++)
					sb.append(w[j]).append(" ");
			}
			keywords = sb.toString().trim();
			casebrief.setCore(keywords);
			Iterator<String> iterator = this.keywords.getKeywordsIterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				if(keywords.contains(key)){
					if(keywordMap.containsKey(key)){
						int count = keywordMap.get(key)+1;
						keywordMap.put(key, count);
					}
					else{
						keywordMap.put(key, 1);
					}
				}
			}
			String caseBrief = wenshu.getCaseBrief();
			if(briefMap.containsKey(caseBrief)){
				int count = briefMap.get(caseBrief)+1;
				briefMap.put(caseBrief, count);
			}
			else{
				briefMap.put(caseBrief, 1);
			}
			String courtLevel = wenshu.getCourtLevel();
			if(courtLevelMap.containsKey(courtLevel)){
				int count = courtLevelMap.get(courtLevel)+1;
				courtLevelMap.put(courtLevel, count);
			}
			else{
				courtLevelMap.put(courtLevel, 1);
			}
			String year = wenshu.getCaseYear();
			if(yearMap.containsKey(year)){
				int count = yearMap.get(year)+1;
				yearMap.put(year, count);
			}
			else{
				yearMap.put(year, 1);
			}
			String documentType = wenshu.getDocumentName();
			if(documentTypeMap.containsKey(documentType)){
				int count = documentTypeMap.get(documentType)+1;
				documentTypeMap.put(documentType, count);
			}
			else{
				documentTypeMap.put(documentType, 1);
			}
		}
		connection.release();
		//下面处理CaseFilter:关键词、案由、法院层级、年份、文书类型
		int id = 0;
		List<CaseFilter> listKeyword = new ArrayList<CaseFilter>();
		listKeyword.add(new CaseFilter(id,"按关键词筛选/",0,true));
		Iterator<String> iterator = keywordMap.keySet().iterator();
		int c1 = 100;
		while(iterator.hasNext()){
			String key = iterator.next();
			int count = keywordMap.get(key);
			String name = "按关键词筛选/"+key;
			listKeyword.get(0).increase(count);
			listKeyword.add(new CaseFilter(id+(c1++),name,count,false));
		}
		id++;
		List<CaseFilter> listBrief = new ArrayList<CaseFilter>();
		listBrief.add(new CaseFilter(id,"按案由筛选/",0,true));
		iterator = briefMap.keySet().iterator();
		int c2 = 200;
		while(iterator.hasNext()){
			String key = iterator.next();
			int count = briefMap.get(key);
			String name = "按案由筛选/"+key;
			listBrief.get(0).increase(count);
			listBrief.add(new CaseFilter(id+(c2++),name,count,false));
		}
		id++;
		List<CaseFilter> listLevel = new ArrayList<CaseFilter>();
		listLevel.add(new CaseFilter(id,"按法院层级筛选/",0,true));
		iterator = courtLevelMap.keySet().iterator();
		int c3 = 300;
		while(iterator.hasNext()){
			String key = iterator.next();
			int count = courtLevelMap.get(key);
			String name = "按法院层级筛选/"+key;
			listLevel.get(0).increase(count);
			listLevel.add(new CaseFilter(id+(c3++),name,count,false));
		}
		id++;
		List<CaseFilter> listYear = new ArrayList<CaseFilter>();
		listYear.add(new CaseFilter(id,"按年份筛选/",0,true));
		iterator = yearMap.keySet().iterator();
		int c4 = 400;
		while(iterator.hasNext()){
			String key = iterator.next();
			int count = yearMap.get(key);
			String name = "按年份筛选/"+key;
			listYear.get(0).increase(count);
			listYear.add(new CaseFilter(id+(c4++),name,count,false));
		}
		id++;
		List<CaseFilter> listType = new ArrayList<CaseFilter>();
		listType.add(new CaseFilter(id,"按文书类型筛选/",0,true));
		iterator = documentTypeMap.keySet().iterator();
		int c5 = 500;
		while(iterator.hasNext()){
			String key = iterator.next();
			int count = documentTypeMap.get(key);
			String name = "按文书类型筛选/"+key;
			listType.get(0).increase(count);
			listType.add(new CaseFilter(id+(c5++),name,count,false));
		}
		Collections.sort(listKeyword,new Comparator<CaseFilter>(){
			@Override
			public int compare(CaseFilter arg0, CaseFilter arg1) {
				int count0 = arg0.getNum();
				int count1 = arg1.getNum();
				if(count0 > count1)
					return -1;
				else if(count0 < count1)
					return 1;
				else
					return 0;
			}
		});
		Collections.sort(listBrief,new Comparator<CaseFilter>(){
			@Override
			public int compare(CaseFilter o1, CaseFilter o2) {
				int count0 = o1.getNum();
				int count1 = o2.getNum();
				if(count0 > count1)
					return -1;
				else if(count0 < count1)
					return 1;
				else
					return 0;
			}
		});
		Collections.sort(listLevel,new Comparator<CaseFilter>(){
			@Override
			public int compare(CaseFilter o1, CaseFilter o2) {
				int count0 = o1.getNum();
				int count1 = o2.getNum();
				if(count0 > count1)
					return -1;
				else if(count0 < count1)
					return 1;
				else
					return 0;
			}
		});
		Collections.sort(listYear,new Comparator<CaseFilter>(){
			@Override
			public int compare(CaseFilter o1, CaseFilter o2) {
				int year1 = 0;
				try{
					year1 = Integer.parseInt(o1.getName().split("/")[1].trim());
				} catch (Exception e){
					return -1;
				}
				int year2 = 0;
				try{
					year2 = Integer.parseInt(o2.getName().split("/")[1].trim());
				} catch(Exception e){
					return 1;
				}
				if(year1 > year2)
					return -1;
				else if(year1 < year2)
					return 1;
				else
					return 0;
			}
		});
		Collections.sort(listType,new Comparator<CaseFilter>(){
			@Override
			public int compare(CaseFilter o1, CaseFilter o2) {
				int count0 = o1.getNum();
				int count1 = o2.getNum();
				if(count0 > count1)
					return -1;
				else if(count0 < count1)
					return 1;
				else
					return 0;
			}
		});
		result.addCaseFilter(listKeyword);
		result.addCaseFilter(listBrief);
		result.addCaseFilter(listLevel);
		result.addCaseFilter(listYear);
		result.addCaseFilter(listType);
		return result;
	}
	@Override
	public CaseSearchRes search(SearchInfo info, CaseFilter filter) {
		System.out.println(filter.getName());
		CaseSearchRes res0 = this.search(info);
		String path = filter.getName();
		String[] e = path.split("/");
		String key = e[0];
		String value = e[1];
		List<CaseBrief> briefs = res0.getBrief();
		Iterator<CaseBrief> iter = briefs.iterator();
		while(iter.hasNext()){
			CaseBrief cb = iter.next();
			if(key.equals("按关键词筛选")){
				if(!cb.getCore().contains(value)){
					iter.remove();
				}
			}
			else if(key.equals("按案由筛选")){
				if(!cb.getBrief().equals(value)){
					iter.remove();
				}
			}
			else if(key.equals("按法院层级筛选")){
				if(!cb.getCourtLevel().equals(value)){
					iter.remove();
				}
			}
			else if(key.equals("按年份筛选")){
				if(!cb.getYear().equals(value)){
					iter.remove();
				}
			}
			else if(key.equals("按文书类型筛选")){
				if(!cb.getType_text().equals(value)){
					iter.remove();
				}
			}
		}
		System.out.println("剩余个数："+briefs.size());
		Map<String,Integer> keywordMap = new HashMap<String,Integer>();//按关键词筛选
		Map<String,Integer> briefMap = new HashMap<String,Integer>();//按案由筛选
		Map<String,Integer> courtLevelMap = new HashMap<String,Integer>();//按法院层级筛选
		Map<String,Integer> yearMap = new HashMap<String,Integer>();//按年份筛选
		Map<String,Integer> documentTypeMap = new HashMap<String,Integer>();//按文书类型筛选
		int size = briefs.size();
		for(int i = 0;i < size;i++){
			CaseBrief brief = briefs.get(i);
			String core = brief.getCore();
			if(core != null && !core.equals("")){
				String[] words = core.split(" ");
				for(String word:words){
					if(keywordMap.containsKey(word)){
						int count = keywordMap.get(word)+1;
						keywordMap.put(word, count);
					}
					else{
						keywordMap.put(word, 1);
					}
				}			
			}
			if(briefMap.containsKey(brief.getBrief())){
				int count = briefMap.get(brief.getBrief())+1;
				briefMap.put(brief.getBrief(), count);
			}
			else{
				briefMap.put(brief.getBrief(), 1);
			}
			if(courtLevelMap.containsKey(brief.getCourtLevel())){
				int count = courtLevelMap.get(brief.getCourtLevel())+1;
				courtLevelMap.put(brief.getCourtLevel(),count);
			}
			else{
				courtLevelMap.put(brief.getCourtLevel(),1);
			}
			if(yearMap.containsKey(brief.getYear())){
				int count = yearMap.get(brief.getYear())+1;
				yearMap.put(brief.getYear(), count);
			}
			else{
				yearMap.put(brief.getYear(), 1);
			}
			if(documentTypeMap.containsKey(brief.getType_text())){
				int count = documentTypeMap.get(brief.getType_text())+1;
				documentTypeMap.put(brief.getType_text(), count);
			}
			else{
				documentTypeMap.put(brief.getType_text(), 1);
			}
		}
		//下面处理CaseFilter:关键词、案由、法院层级、年份、文书类型
		int id = 0;
		List<CaseFilter> listKeyword = new ArrayList<CaseFilter>();
		listKeyword.add(new CaseFilter(id,"按关键词筛选/",0,true));
		Iterator<String> iterator = keywordMap.keySet().iterator();
		int c1 = 100;
		while(iterator.hasNext()){
			String key1 = iterator.next();
			int count = keywordMap.get(key1);
			String name = "按关键词筛选/"+key1;
			listKeyword.get(0).increase(count);
			listKeyword.add(new CaseFilter(id+(c1++),name,count,false));
		}
		id++;
		List<CaseFilter> listBrief = new ArrayList<CaseFilter>();
		listBrief.add(new CaseFilter(id,"按案由筛选/",0,true));
		iterator = briefMap.keySet().iterator();
		int c2 = 200;
		while(iterator.hasNext()){
			String key2 = iterator.next();
			int count = briefMap.get(key2);
			String name = "按案由筛选/"+key2;
			listBrief.get(0).increase(count);
			listBrief.add(new CaseFilter(id+(c2++),name,count,false));
		}
		id++;
		List<CaseFilter> listLevel = new ArrayList<CaseFilter>();
		listLevel.add(new CaseFilter(id,"按法院层级筛选/",0,true));
		iterator = courtLevelMap.keySet().iterator();
		int c3 = 300;
		while(iterator.hasNext()){
			String key3 = iterator.next();
			int count = courtLevelMap.get(key);
			String name = "按法院层级筛选/"+key3;
			listLevel.get(0).increase(count);
			listLevel.add(new CaseFilter(id+(c3++),name,count,false));
		}
		id++;
		List<CaseFilter> listYear = new ArrayList<CaseFilter>();
		listYear.add(new CaseFilter(id,"按年份筛选/",0,true));
		iterator = yearMap.keySet().iterator();
		int c4 = 400;
		while(iterator.hasNext()){
			String key4 = iterator.next();
			int count = yearMap.get(key4);
			String name = "按年份筛选/"+key4;
			listYear.get(0).increase(count);
			listYear.add(new CaseFilter(id+(c4++),name,count,false));
		}
		id++;
		List<CaseFilter> listType = new ArrayList<CaseFilter>();
		listType.add(new CaseFilter(id,"按文书类型筛选/",0,true));
		iterator = documentTypeMap.keySet().iterator();
		int c5 = 500;
		while(iterator.hasNext()){
			String key5 = iterator.next();
			int count = documentTypeMap.get(key5);
			String name = "按文书类型筛选/"+key5;
			listType.get(0).increase(count);
			listType.add(new CaseFilter(id+(c5++),name,count,false));
		}
		Collections.sort(listKeyword,new Comparator<CaseFilter>(){
			@Override
			public int compare(CaseFilter arg0, CaseFilter arg1) {
				int count0 = arg0.getNum();
				int count1 = arg1.getNum();
				if(count0 > count1)
					return -1;
				else if(count0 < count1)
					return 1;
				else
					return 0;
			}
		});
		Collections.sort(listBrief,new Comparator<CaseFilter>(){
			@Override
			public int compare(CaseFilter o1, CaseFilter o2) {
				int count0 = o1.getNum();
				int count1 = o2.getNum();
				if(count0 > count1)
					return -1;
				else if(count0 < count1)
					return 1;
				else
					return 0;
			}
		});
		Collections.sort(listLevel,new Comparator<CaseFilter>(){
			@Override
			public int compare(CaseFilter o1, CaseFilter o2) {
				int count0 = o1.getNum();
				int count1 = o2.getNum();
				if(count0 > count1)
					return -1;
				else if(count0 < count1)
					return 1;
				else
					return 0;
			}
		});
		Collections.sort(listYear,new Comparator<CaseFilter>(){
			@Override
			public int compare(CaseFilter o1, CaseFilter o2) {
				int year1 = 0;
				try{
					year1 = Integer.parseInt(o1.getName().split("/")[1].trim());
				} catch (Exception e){
					return -1;
				}
				int year2 = 0;
				try{
					year2 = Integer.parseInt(o2.getName().split("/")[1].trim());
				} catch(Exception e){
					return 1;
				}
				if(year1 > year2)
					return -1;
				else if(year1 < year2)
					return 1;
				else
					return 0;
			}
		});
		Collections.sort(listType,new Comparator<CaseFilter>(){
			@Override
			public int compare(CaseFilter o1, CaseFilter o2) {
				int count0 = o1.getNum();
				int count1 = o2.getNum();
				if(count0 > count1)
					return -1;
				else if(count0 < count1)
					return 1;
				else
					return 0;
			}
		});
		CaseSearchRes res = new CaseSearchRes();
		res.setBrief(briefs);
		res.addCaseFilter(listKeyword);
		res.addCaseFilter(listBrief);
		res.addCaseFilter(listLevel);
		res.addCaseFilter(listYear);
		res.addCaseFilter(listType);
		return res;
	}
	@Override
	public CaseSearchRes search(CaseSearchRes res0, CaseFilter filter) {
		String path = filter.getName();
		System.out.println(path);
		String[] e = path.split("/");
		String key = e[0];
		String value = e[1];
		List<CaseBrief> briefs = res0.getBrief();
		Iterator<CaseBrief> iter = briefs.iterator();
		while(iter.hasNext()){
			CaseBrief cb = iter.next();
			if(key.equals("按关键词筛选")){
				if(!cb.getCore().contains(value)){
					iter.remove();
				}
			}
			else if(key.equals("按案由筛选")){
				if(!cb.getBrief().equals(value)){
					iter.remove();
				}
			}
			else if(key.equals("按法院层级筛选")){
				if(!cb.getCourtLevel().equals(value)){
					iter.remove();
				}
			}
			else if(key.equals("按年份筛选")){
				if(!cb.getYear().equals(value)){
					iter.remove();
				}
			}
			else if(key.equals("按文书类型筛选")){
				if(!cb.getType_text().equals(value)){
					iter.remove();
				}
			}
		}
		System.out.println("剩余个数："+briefs.size());
		Map<String,Integer> keywordMap = new HashMap<String,Integer>();//按关键词筛选
		Map<String,Integer> briefMap = new HashMap<String,Integer>();//按案由筛选
		Map<String,Integer> courtLevelMap = new HashMap<String,Integer>();//按法院层级筛选
		Map<String,Integer> yearMap = new HashMap<String,Integer>();//按年份筛选
		Map<String,Integer> documentTypeMap = new HashMap<String,Integer>();//按文书类型筛选
		int size = briefs.size();
		for(int i = 0;i < size;i++){
			CaseBrief brief = briefs.get(i);
			String core = brief.getCore();
			if(core != null && !core.equals("")){
				String[] words = core.split(" ");
				for(String word:words){
					if(keywordMap.containsKey(word)){
						int count = keywordMap.get(word)+1;
						keywordMap.put(word, count);
					}
					else{
						keywordMap.put(word, 1);
					}
				}			
			}
			if(briefMap.containsKey(brief.getBrief())){
				int count = briefMap.get(brief.getBrief())+1;
				briefMap.put(brief.getBrief(), count);
			}
			else{
				briefMap.put(brief.getBrief(), 1);
			}
			if(courtLevelMap.containsKey(brief.getCourtLevel())){
				int count = courtLevelMap.get(brief.getCourtLevel())+1;
				courtLevelMap.put(brief.getCourtLevel(),count);
			}
			else{
				courtLevelMap.put(brief.getCourtLevel(),1);
			}
			if(yearMap.containsKey(brief.getYear())){
				int count = yearMap.get(brief.getYear())+1;
				yearMap.put(brief.getYear(), count);
			}
			else{
				yearMap.put(brief.getYear(), 1);
			}
			if(documentTypeMap.containsKey(brief.getType_text())){
				int count = documentTypeMap.get(brief.getType_text())+1;
				documentTypeMap.put(brief.getType_text(), count);
			}
			else{
				documentTypeMap.put(brief.getType_text(), 1);
			}
		}
		//下面处理CaseFilter:关键词、案由、法院层级、年份、文书类型
		int id = 0;
		List<CaseFilter> listKeyword = new ArrayList<CaseFilter>();
		listKeyword.add(new CaseFilter(id,"按关键词筛选/",0,true));
		Iterator<String> iterator = keywordMap.keySet().iterator();
		int c1 = 100;
		while(iterator.hasNext()){
			String key1 = iterator.next();
			int count = keywordMap.get(key1);
			String name = "按关键词筛选/"+key1;
			listKeyword.get(0).increase(count);
			listKeyword.add(new CaseFilter(id+(c1++),name,count,false));
		}
		id++;
		List<CaseFilter> listBrief = new ArrayList<CaseFilter>();
		listBrief.add(new CaseFilter(id,"按案由筛选/",0,true));
		iterator = briefMap.keySet().iterator();
		int c2 = 200;
		while(iterator.hasNext()){
			String key2 = iterator.next();
			int count = briefMap.get(key2);
			String name = "按案由筛选/"+key2;
			listBrief.get(0).increase(count);
			listBrief.add(new CaseFilter(id+(c2++),name,count,false));
		}
		id++;
		List<CaseFilter> listLevel = new ArrayList<CaseFilter>();
		listLevel.add(new CaseFilter(id,"按法院层级筛选/",0,true));
		iterator = courtLevelMap.keySet().iterator();
		int c3 = 300;
		while(iterator.hasNext()){
			String key3 = iterator.next();
			int count = courtLevelMap.get(key3);
			String name = "按法院层级筛选/"+key3;
			listLevel.get(0).increase(count);
			listLevel.add(new CaseFilter(id+(c3++),name,count,false));
		}
		id++;
		List<CaseFilter> listYear = new ArrayList<CaseFilter>();
		listYear.add(new CaseFilter(id,"按年份筛选/",0,true));
		iterator = yearMap.keySet().iterator();
		int c4 = 400;
		while(iterator.hasNext()){
			String key4 = iterator.next();
			int count = yearMap.get(key4);
			String name = "按年份筛选/"+key4;
			listYear.get(0).increase(count);
			listYear.add(new CaseFilter(id+(c4++),name,count,false));
		}
		id++;
		List<CaseFilter> listType = new ArrayList<CaseFilter>();
		listType.add(new CaseFilter(id,"按文书类型筛选/",0,true));
		iterator = documentTypeMap.keySet().iterator();
		int c5 = 500;
		while(iterator.hasNext()){
			String key5 = iterator.next();
			int count = documentTypeMap.get(key5);
			String name = "按文书类型筛选/"+key5;
			listType.get(0).increase(count);
			listType.add(new CaseFilter(id+(c5++),name,count,false));
		}
		Collections.sort(listKeyword,new Comparator<CaseFilter>(){
			@Override
			public int compare(CaseFilter arg0, CaseFilter arg1) {
				int count0 = arg0.getNum();
				int count1 = arg1.getNum();
				if(count0 > count1)
					return -1;
				else if(count0 < count1)
					return 1;
				else
					return 0;
			}
		});
		Collections.sort(listBrief,new Comparator<CaseFilter>(){
			@Override
			public int compare(CaseFilter o1, CaseFilter o2) {
				int count0 = o1.getNum();
				int count1 = o2.getNum();
				if(count0 > count1)
					return -1;
				else if(count0 < count1)
					return 1;
				else
					return 0;
			}
		});
		Collections.sort(listLevel,new Comparator<CaseFilter>(){
			@Override
			public int compare(CaseFilter o1, CaseFilter o2) {
				int count0 = o1.getNum();
				int count1 = o2.getNum();
				if(count0 > count1)
					return -1;
				else if(count0 < count1)
					return 1;
				else
					return 0;
			}
		});
		Collections.sort(listYear,new Comparator<CaseFilter>(){
			@Override
			public int compare(CaseFilter o1, CaseFilter o2) {
				int year1 = 0;
				try{
					year1 = Integer.parseInt(o1.getName().split("/")[1].trim());
				} catch (Exception e){
					return -1;
				}
				int year2 = 0;
				try{
					year2 = Integer.parseInt(o2.getName().split("/")[1].trim());
				} catch(Exception e){
					return 1;
				}
				if(year1 > year2)
					return -1;
				else if(year1 < year2)
					return 1;
				else
					return 0;
			}
		});
		Collections.sort(listType,new Comparator<CaseFilter>(){
			@Override
			public int compare(CaseFilter o1, CaseFilter o2) {
				int count0 = o1.getNum();
				int count1 = o2.getNum();
				if(count0 > count1)
					return -1;
				else if(count0 < count1)
					return 1;
				else
					return 0;
			}
		});
		CaseSearchRes res = new CaseSearchRes();
		res.setBrief(briefs);
		res.addCaseFilter(listKeyword);
		res.addCaseFilter(listBrief);
		res.addCaseFilter(listLevel);
		res.addCaseFilter(listYear);
		res.addCaseFilter(listType);
		return res;
	}
}