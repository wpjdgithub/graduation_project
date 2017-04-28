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
			getResult();
			System.out.println("获取了一次数据");
		}
		
		
		getFirstPage();
		
		return SUCCESS;
	}
	
	public String advanced() throws ServletException, IOException {
		System.out.println(info.getBrief());
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	private void getResult(){
		/*ArrayList<CaseBrief> caselist = new ArrayList<CaseBrief>();
		CaseBrief brief = new CaseBrief(123214,"标题1","浙江省法院","2017-01-01",
				"案由1","审批程序1","文书类型1","来源1","核心词汇1");
		CaseBrief brief2 = new CaseBrief(123214,"标题2","浙江省法院","2017-01-01",
				"案由1","审批程序1","文书类型1","来源1","核心词汇1");
		CaseBrief brief3 = new CaseBrief(123214,"标题3","浙江省法院","2017-01-01",
				"案由1","审批程序1","文书类型1","来源1","核心词汇1");
		CaseBrief brief4 = new CaseBrief(123214,"标题4","浙江省法院","2017-01-01",
				"案由1","审批程序1","文书类型1","来源1","核心词汇1");
		CaseBrief brief5 = new CaseBrief(123214,"标题5","浙江省法院","2017-01-01",
				"案由1","审批程序1","文书类型1","来源1","核心词汇1");
		caselist.add(brief);
		caselist.add(brief);
		caselist.add(brief);
		caselist.add(brief);
		caselist.add(brief);
		caselist.add(brief2);
		caselist.add(brief2);
		caselist.add(brief2);
		caselist.add(brief2);
		caselist.add(brief2);
		caselist.add(brief3);
		caselist.add(brief3);
		caselist.add(brief3);
		caselist.add(brief3);
		caselist.add(brief3);
		caselist.add(brief4);
		caselist.add(brief4);
		caselist.add(brief4);
		caselist.add(brief4);
		caselist.add(brief4);
		caselist.add(brief5);
		caselist.add(brief5);
		caselist.add(brief5);
		caselist.add(brief5);
		caselist.add(brief5);
		caselist.add(brief);
		caselist.add(brief);
		caselist.add(brief);
		caselist.add(brief);
		caselist.add(brief);
		caselist.add(brief);
		caselist.add(brief);
		caselist.add(brief);
		caselist.add(brief);
		caselist.add(brief);
		caselist.add(brief);
		caselist.add(brief);
		caselist.add(brief);
		caselist.add(brief);
		caselist.add(brief);
		caselist.add(brief);
		caselist.add(brief);
		caselist.add(brief);
		caselist.add(brief);
		*/
		System.out.println(request.getRealPath("/"));
		service = new SearchServiceImpl(request.getRealPath("/"));
		CaseSearchRes res = service.search(input);
		List<CaseBrief> caselist = res.getBrief();
		List<CaseFilter> filter = res.getFilter();
		checkFilter(filter);
		
		for(CaseFilter f:filter){
			System.out.println(f.getId()+" "+f.getName());
		}
		
		session.put("AllData", caselist);
		session.put("maxPage", (caselist.size()/5)+((caselist.size()%5==0)?0:1));
		session.put("AllFilter", filter);
		
		/*List<CaseFilter> filter = new ArrayList<CaseFilter>();c
		filter.add(new CaseFilter(5,"2/",1,false));
		filter.add(new CaseFilter(6,"2/1/",34,false));
		filter.add(new CaseFilter(7,"3/",67,false));
		filter.add(new CaseFilter(8,"3/1/",25,false));
		filter.add(new CaseFilter(9,"3/2/",52,false));
		filter.add(new CaseFilter(10,"3/3/",34,false));
		filter.add(new CaseFilter(11,"3/2/1/",51,false));
		*/
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
			String[] fl = filter.getName().split("/");
			boolean hasChild = false;
			for(CaseFilter cs:list){
				if(cs.getName().startsWith(filter.getName())&&cs.getName().length()>filter.getName().length()){
					hasChild = true;
					break;
				}
			}
			filter.setHasChild(hasChild);
		}
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
