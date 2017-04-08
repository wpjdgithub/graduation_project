package Grad.Action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;

import com.google.gson.Gson;

import Grad.Bean.CaseBrief;
import Grad.Bean.CaseFilter;
import net.sf.json.JSONArray;

@Controller
public class CaseManageAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5676244007691311829L;
	
	private String sortType;
	private int pageNum;
	private int filterId;

	private String CaseResult;
	private String FilterResult;
	
	public String page() throws ServletException,IOException{
		getThisPage();
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String sort() throws ServletException,IOException{
		ArrayList<CaseBrief> caselist = (ArrayList<CaseBrief>) session.get("AllData");
		caselist.remove(1);
		session.put("AllData", caselist);
		session.put("maxPage", (caselist.size()/5)+((caselist.size()%5==0)?0:1));
		pageNum = 1;
		getThisPage();
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String filter() throws ServletException,IOException{
		getFilter();
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	private void getThisPage(){
		pageNum = pageNum==0?1:pageNum;
		ArrayList<CaseBrief> list = (ArrayList<CaseBrief>) session.get("AllData");
		int max = ((pageNum*5)>list.size())?list.size():(pageNum*5);
		ArrayList<CaseBrief> pageList = new ArrayList<CaseBrief>();
		for(int i=(pageNum*5-5);i<max;i++){
			pageList.add(list.get(i));
		}
		Gson gson = new Gson();
		CaseResult = gson.toJson(pageList);
	}
	
	private void getFilter(){
		if(filterId==0){
			FilterResult = "{'status':'empty'}";
		}else{
			List<CaseFilter> list = (ArrayList<CaseFilter>) session.get("AllFilter");
			String path = "";
			for(CaseFilter filter:list){
				if(filterId==filter.getId())
					path = filter.getName();
			}
			String[] pathl = path.split("/");
			List<CaseFilter> result = new ArrayList<CaseFilter>();
			for(CaseFilter filter:list){
				if(filter.getName().startsWith(path)
						&&filter.getName().split("/").length==(pathl.length+1)){
					result.add(filter);
				}
			}
			Gson gson = new Gson();
			FilterResult = gson.toJson(result);
		}
	}
	

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getCaseResult() {
		return CaseResult;
	}

	public void setCaseResult(String CaseResult) {
		this.CaseResult = CaseResult;
	}
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public String getFilterResult() {
		return FilterResult;
	}

	public void setFilterResult(String filterResult) {
		FilterResult = filterResult;
	}

	public int getFilterId() {
		return filterId;
	}

	public void setFilterId(int filterId) {
		this.filterId = filterId;
	}

}
