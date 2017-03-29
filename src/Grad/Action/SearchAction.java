package Grad.Action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import Grad.Bean.CaseBrief;
import Grad.Bean.SearchInfo;
import Grad.Service.SearchService;

@Controller
public class SearchAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String input;
	private SearchInfo info;
	private int pageNum;
	private ArrayList<CaseBrief> caselist;
	
	/*@Autowired
	@Qualifier("normal")
	private SearchService service_n;
	
	@Autowired
	@Qualifier("advanced")
	private SearchService service_a;
	*/

	public String normal() throws ServletException, IOException {
		caselist = new ArrayList<CaseBrief>();
		caselist.add(new CaseBrief());
		return SUCCESS;
	}
	
	public String advanced() throws ServletException, IOException {
		
		return SUCCESS;
	}
	
	private void pageChange(int page){
		
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
