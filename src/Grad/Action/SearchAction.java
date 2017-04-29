package Grad.Action;

import java.io.File;

import java.io.IOException;

import java.util.ArrayList;
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


	public String normal() throws ServletException, IOException {
		
		if(input!=null){
			getResult(1);
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
		checkFilter(filter);
		
		for(CaseFilter f:filter){
			System.out.println(f.getId()+" "+f.getName()+" "+f.getNum());
		}
		
		session.put("AllData", caselist);
		session.put("maxPage", (caselist.size()/5)+((caselist.size()%5==0)?0:1));
		session.put("AllFilter", filter);
		
	}
	
	private CaseSearchRes getResByNor(){
		return service.search(input);
	}
	
	private CaseSearchRes getResByAdv(){
		return service.search(info);
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
	
	private void checkFilter(List<CaseFilter> list){
		for(CaseFilter filter:list){
			boolean hasChild = false;
			int len = filter.getName().split("/").length;
			for(CaseFilter cs:list){
				if(cs.getName().startsWith(filter.getName())&&cs.getName().split("/").length>len){
					hasChild = true;
					break;
				}
			}
			filter.setHasChild(hasChild);
		}
	}
	
	private void init(){
		service = new SearchServiceImpl(request.getRealPath("/"));
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
}
