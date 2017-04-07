package Grad.Action;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;

import Grad.Bean.CaseBrief;
import Grad.Bean.SearchInfo;
import net.sf.json.JSONObject;

@Controller
public class SearchAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String input;
	private SearchInfo info;
	
	/*@Autowired
	@Qualifier("normal")
	private SearchService service_n;
	
	@Autowired
	@Qualifier("advanced")
	private SearchService service_a;
	*/

	public String normal() throws ServletException, IOException {
		
		if(input!=null){
			getResult();
			System.out.println("获取了一次数据");
		}
		
		
		getFirstPage();
		
		return SUCCESS;
	}
	
	public String advanced() throws ServletException, IOException {
		System.out.println(info.getSdate());
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	private void getResult(){
		ArrayList<CaseBrief> caselist = new ArrayList<CaseBrief>();
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
		session.put("AllData", caselist);
		session.put("maxPage", (caselist.size()/5)+((caselist.size()%5==0)?0:1));
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
}
