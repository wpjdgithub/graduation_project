package Grad.Action;



import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;

import Grad.Bean.CaseBrief;
import Grad.Bean.CaseFilter;
import Grad.Bean.CaseSearchRes;
import Grad.Bean.SearchInfo;
import Grad.Service.SearchService;
import Grad.Service.searchservice.SearchServiceImpl;


@Controller
public class SearchAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String input;
	private SearchInfo info;
	
	private SearchService service;

	private List<CaseFilter> l1 = new ArrayList<CaseFilter>();
	private List<CaseFilter> l2 = new ArrayList<CaseFilter>();
	private List<CaseFilter> l3 = new ArrayList<CaseFilter>();
	private List<CaseFilter> l4 = new ArrayList<CaseFilter>();
	private List<CaseFilter> l5 = new ArrayList<CaseFilter>();
	
	


	@SuppressWarnings("unchecked")
	public String normal() throws ServletException, IOException {
		
		if(input!=null){
			getResult(1);
			session.put("input", input);
			System.out.println("获取了一次数据");
		}
		
		
		getFirstPage();
		
		return SUCCESS;
	}
	
	public String advanced() throws ServletException, IOException {
		init();

		getResult(2);
		
		getFirstPage();
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String filter() throws ServletException, IOException {
		if(input!=null){
			getResult(3);
			System.out.println("筛选了一次数据");
		}
		
		getFirstPage();
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	private void getResult(int kind){
		init();
		CaseSearchRes res;
		if(kind==1)
			res = getResByNor();
		else if(kind==2)
			res = getResByAdv();
		else
			res = getResByFil();
		
		List<CaseBrief> caselist = res.getBrief();
		List<CaseFilter> filter = res.getFilter();
		filter_solve(filter);
		
		
		session.put("AllData", caselist);
		session.put("maxPage", (caselist.size()/5)+((caselist.size()%5==0)?0:1));
		session.put("AllFilter", filter);
		
		session.put("key_data", getPie_data(l1));
		session.put("case_data", getPie_data(l2));
		session.put("court_data", getPie_data(l3));
		session.put("year_data", getPie_data(l4));
		session.put("text_data", getPie_data(l5));
	}
	
	private String getPie_data(List<CaseFilter> filter){
		int size = filter.size();
		if(size==0){
			return "['暂无相关数据',2]";
		}
		
		if(size>5){
			size=5;
		}
		
		String res = "[";
		for(int i=0;i<(size-1);i++){
			CaseFilter f = filter.get(i);
			res = res+"['"+f.getLastPath()+"',"+String.valueOf(f.getNum())+"],";
		}
		
		CaseFilter f = filter.get(size-1);
		res = res+"['"+f.getLastPath()+"',"+String.valueOf(f.getNum())+"]";
		
		res = res + "]";
		return res;
	}
	
	private CaseSearchRes getResByNor(){
		return service.search(input);
	}
	
	private CaseSearchRes getResByAdv(){
		return service.search(info);
	}
	
	private void filter_solve(List<CaseFilter> filter){
		
		l1.clear();
		l2.clear();
		l3.clear();
		l4.clear();
		l5.clear();
		
		
		for(CaseFilter f:filter){
			if(f.getName().startsWith("按关键字筛选/")&& f.getName().split("/").length>1){
				l1.add(new CaseFilter(f.getId(),f.getLastPath(),f.getNum(),f.isHasChild()));
			}else if(f.getName().startsWith("按案由筛选/")&& f.getName().split("/").length>1){
				l2.add(new CaseFilter(f.getId(),f.getLastPath(),f.getNum(),f.isHasChild()));
			}else if(f.getName().startsWith("按法院层级筛选/")&& f.getName().split("/").length>1){
				l3.add(new CaseFilter(f.getId(),f.getLastPath(),f.getNum(),f.isHasChild()));
			}else if(f.getName().startsWith("按年份筛选/")&& f.getName().split("/").length>1){
				l4.add(new CaseFilter(f.getId(),f.getLastPath(),f.getNum(),f.isHasChild()));
			}else if(f.getName().startsWith("按文书类型筛选/")&& f.getName().split("/").length>1){
				l5.add(new CaseFilter(f.getId(),f.getLastPath(),f.getNum(),f.isHasChild()));
			}
		}
		
		Collections.reverse(l1);
		Collections.reverse(l2);
		Collections.reverse(l3);
		Collections.reverse(l4);
		Collections.reverse(l5);
	}
	
	@SuppressWarnings("unchecked")
	private CaseSearchRes getResByFil(){
		List<CaseBrief> brief = (List<CaseBrief>) session.get("AllData");
		List<CaseFilter> filter = (List<CaseFilter>) session.get("AllFilter");
		CaseSearchRes res = new CaseSearchRes(brief, filter);
		
		CaseFilter filter_choosed = new CaseFilter();
		for(CaseFilter f:filter){
			if(input.equals(String.valueOf(f.getId()))){
				filter_choosed = f;
			}
		}
		//System.out.println(filter_choosed.getName());
		return service.search(res, filter_choosed);
	}
	
	@SuppressWarnings("unchecked")
	private void getFirstPage(){
		ArrayList<CaseBrief> list = (ArrayList<CaseBrief>) session.get("AllData");
		int max = ((1*5)>list.size())?list.size():(1*5);
		ArrayList<CaseBrief> pageList = new ArrayList<CaseBrief>();
		for(int i=(1*5-5);i<max;i++){
			pageList.add(list.get(i));
		}
		session.put("pageData", pageList);
	}
	
	

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public SearchInfo getInfo() {
		return info;
	}

	public void setInfo(SearchInfo info) {
		this.info = info;
	}
	
	private void init(){
		service = new SearchServiceImpl(request.getRealPath("/"));
	}

	public List<CaseFilter> getL1() {
		return l1;
	}

	public void setL1(List<CaseFilter> l1) {
		this.l1 = l1;
	}

	public List<CaseFilter> getL2() {
		return l2;
	}

	public void setL2(List<CaseFilter> l2) {
		this.l2 = l2;
	}

	public List<CaseFilter> getL3() {
		return l3;
	}

	public void setL3(List<CaseFilter> l3) {
		this.l3 = l3;
	}

	public List<CaseFilter> getL4() {
		return l4;
	}

	public void setL4(List<CaseFilter> l4) {
		this.l4 = l4;
	}

	public List<CaseFilter> getL5() {
		return l5;
	}

	public void setL5(List<CaseFilter> l5) {
		this.l5 = l5;
	}
}
