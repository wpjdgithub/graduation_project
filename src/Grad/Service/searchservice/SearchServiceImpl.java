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
		String caseName = info.getName_case();//TODO
		String caseID = info.getNum();
		String courtLevel = info.getCourtLevel();
		String caseType = info.getCaseType();
		String caseProgram = info.getCaseProgram();
		String documentType = info.getDocumentType();
		String judger = info.getJudex();
		String litigant = info.getLitigant();
		String laws = info.getLegistative_authority();
		if(caseID.equals("")){
			items.add(SearchItem.caseid);
			values.add(caseID);
		}
		if(courtLevel.equals("")){
			items.add(SearchItem.courtlevel);
			values.add(courtLevel);
		}
		if(fullText.equals("")){
			items.add(SearchItem.fulltext);
			values.add(fullText);
		}
		if(caseBrief.equals("")){
			items.add(SearchItem.casebrief);
			values.add(caseBrief);
		}
		if(caseName.equals("")){
			//TODO
		}
		if(caseType.equals("")){
			items.add(SearchItem.casetype);
			values.add(caseType);
		}
		if(caseProgram.equals("")){
			items.add(SearchItem.caseprogram);
			values.add(caseProgram);
		}
		if(documentType != null){
			items.add(SearchItem.documenttype);
			values.add(documentType);
		}
		if(judger != null){
			items.add(SearchItem.judgers);
			values.add(judger);
		}
		if(litigant != null){
			items.add(SearchItem.participants);
			values.add(litigant);
		}
		if(laws != null){
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
		int size = list.size();
		for(int i = 0;i < size;i++){
			Wenshu wenshu = list.get(i);
			CaseBrief casebrief = new CaseBrief();
			casebrief.setId(i);//TODO
			casebrief.setTitle(wenshu.getCaseName());
			casebrief.setCaseID(wenshu.getCaseID());
			casebrief.setDate(wenshu.getCaseYear());//TODO
			casebrief.setBrief(wenshu.getCaseBrief());
			casebrief.setProcess_judgement(wenshu.getCaseProgram());
			casebrief.setType_text(wenshu.getDocumentName());
			casebrief.setSource("天津最高人民法院");//TODO
			result.addCaseBrief(casebrief);
			StringBuilder keywordSB = new StringBuilder();
			Iterator<String> iterator = this.keywords.getKeywordsIterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				if(wenshu.getFullText().contains(key)){
					keywordSB.append(key+" ");
					if(keywordMap.containsKey(key)){
						int count = keywordMap.get(key)+1;
						keywordMap.put(key, count);
					}
					else{
						keywordMap.put(key, 1);
					}
				}
			}
			wenshu.setKeywords(keywordSB.toString().trim());
			String keywords = wenshu.getKeywords();
			casebrief.setCore(keywords);
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
			String documentType = wenshu.getDocumentType();
			if(documentTypeMap.containsKey(documentType)){
				int count = documentTypeMap.get(documentType)+1;
				documentTypeMap.put(documentType, count);
			}
			else{
				documentTypeMap.put(documentType, 1);
			}
		}
		//下面处理CaseFilter:关键词、案由、法院层级、年份、文书类型
		int id = 0;
		List<CaseFilter> listKeyword = new ArrayList<CaseFilter>();
		listKeyword.add(new CaseFilter(id,"按关键词筛选/",0,true));
		Iterator<String> iterator = keywordMap.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			int count = keywordMap.get(key);
			String name = "按关键词筛选/"+key;
			listKeyword.get(0).increase(count);
			listKeyword.add(new CaseFilter(id,name,count,false));
		}
		id++;
		List<CaseFilter> listBrief = new ArrayList<CaseFilter>();
		listBrief.add(new CaseFilter(id,"按案由筛选/",0,true));
		iterator = briefMap.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			int count = briefMap.get(key);
			String name = "按案由筛选/"+key;
			listBrief.get(0).increase(count);
			listBrief.add(new CaseFilter(id,name,count,false));
		}
		id++;
		List<CaseFilter> listLevel = new ArrayList<CaseFilter>();
		listLevel.add(new CaseFilter(id,"按法院层级筛选/",0,true));
		iterator = courtLevelMap.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			int count = courtLevelMap.get(key);
			String name = "按法院层级筛选/"+key;
			listLevel.get(0).increase(count);
			listLevel.add(new CaseFilter(id,name,count,false));
		}
		id++;
		List<CaseFilter> listYear = new ArrayList<CaseFilter>();
		listYear.add(new CaseFilter(id,"按年份筛选/",0,true));
		iterator = yearMap.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			int count = yearMap.get(key);
			String name = "按年份筛选/"+key;
			listYear.get(0).increase(count);
			listYear.add(new CaseFilter(id,name,count,false));
		}
		id++;
		List<CaseFilter> listType = new ArrayList<CaseFilter>();
		listType.add(new CaseFilter(id,"按文书类型筛选/",0,true));
		iterator = documentTypeMap.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			int count = documentTypeMap.get(key);
			String name = "按文书类型筛选/"+key;
			listType.get(0).increase(count);
			listType.add(new CaseFilter(id,name,count,false));
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
		String filterName = filter.getName();
		String[] e = filterName.split("/");
		if(e.length == 1){
			CaseSearchRes result = this.search(info);
			return result;
		}
		else{
			String key = e[0];
			String value = e[1];
			if(key.equals("按案由筛选")){
				info.setBrief(value);
			}
			else if(key.equals("按关键词筛选")){
				info.setFull_text(value);
			}
			else if(key.equals("按文书类型筛选")){
				if(value.equals("裁定书")){
					info.setType_text(0);
				}
				else if(value.equals("调解书")){
					info.setType_text(1);
				}
				else if(value.equals("判决书")){
					info.setType_text(2);
				}
			}
			else if(key.equals("按年份筛选")){
				//TODO
			}
			else if(key.equals("按法院层级筛选")){
				if(value.equals("基层")){
					info.setLevel_court(0);
				}
				else if(value.equals("中级")){
					info.setLevel_court(1);
				}
				else if(value.equals("最高")){
					info.setLevel_court(2);
				}
			}
			CaseSearchRes result = this.search(info);
			return result;
		}
	}
}